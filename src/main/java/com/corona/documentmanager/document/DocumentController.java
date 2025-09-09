package com.corona.documentmanager.document;
import com.corona.documentmanager.File.FileManager;
import com.corona.documentmanager.dto.DocumentTagDTO;
import com.corona.documentmanager.exception.DocumentNotFoundException;
import com.corona.documentmanager.File.FileFactory;
import com.corona.documentmanager.File.FileParser;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.documentType.DocumentTypeRepository;
import com.corona.documentmanager.user.LoggedUser;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import com.corona.documentmanager.exception.PermissionDeniedException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final FileFactory fileFactory;


    @Autowired
    public DocumentController(DocumentService documentService,
                              DocumentTypeRepository documentTypeRepository, DocumentRepository documentRepository, FileFactory fileFactory) {
        this.documentService = documentService;
        this.documentTypeRepository = documentTypeRepository;
        this.documentRepository = documentRepository;
        this.fileFactory = fileFactory;
    }

    @GetMapping("/documents/download/{id}")
    @PreAuthorize("@documentPermission.canRead(@documentService.findDocumentById(#id), principal)")
    public ResponseEntity<ByteArrayResource> downloadDocument(Authentication authentication, @PathVariable Long id) {
        try {
            Document document = documentService.findDocumentById(id);
            ByteArrayResource resource = new ByteArrayResource(document.getData());
            return createDocumentResponse(document, resource);
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private ResponseEntity<ByteArrayResource> createDocumentResponse(Document document, ByteArrayResource resource) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", document.getTitle()))
                .contentType(MediaType.parseMediaType(document.getMimeType()))
                .contentLength(resource.contentLength())
                .body(resource);
    }




    @PostMapping("/api/document/")
    public ResponseEntity<Object> newDocument(Authentication authentication,
                                              @RequestPart MultipartFile file,
                                              @RequestPart String title,
                                              @RequestPart String description) {
        try {
            LoggedUser customUser = (LoggedUser) authentication.getPrincipal();

            String mimeType = "";
            try (InputStream stream = file.getInputStream()) {
                mimeType = FileParser.parse(stream);
            } catch (IOException | TikaException | SAXException ignored) {
            }

            Optional<DocumentType> docType = documentTypeRepository.findByType(mimeType);
            if (docType.isEmpty()) {
                DocumentType dt = new DocumentType();
                dt.setType(mimeType);
                documentTypeRepository.save(dt);
                docType = Optional.of(dt);
            }

            FileManager fileManager = fileFactory.getFileManager(mimeType);
            Document newDocument = fileManager.createNewDocument(file, customUser, title, description, mimeType, docType);
            documentRepository.save(newDocument);

            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error" + e.getMessage());
        }
    }


    @PostMapping("/api/upload/multiple")
    public ResponseEntity<?> uploadMultipleFiles(Authentication authentication,
                                                 @RequestParam("files") MultipartFile[] files) {
        try {
            LoggedUser customUser = (LoggedUser) authentication.getPrincipal();
            List<Thread> threads = new ArrayList<>();
            List<Document> results = Collections.synchronizedList(new ArrayList<>());
            CountDownLatch latch = new CountDownLatch(files.length);

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    Thread thread = new Thread(() -> {
                        try {
                            Optional<DocumentType> docType = documentTypeRepository.findByType(file.getContentType());
                            DocumentType documentType;
                            if (docType.isEmpty()) {
                                documentType = new DocumentType();
                                documentType.setType(file.getContentType());
                                documentType = documentTypeRepository.save(documentType);
                            } else {
                                documentType = docType.get();
                            }
                            System.out.println("Documento salvataggio in corso " + file.getOriginalFilename() + " " + documentType.getType());
                            Document document = documentService.processFileAsync(
                                    file,
                                    file.getOriginalFilename(),
                                    "",
                                    customUser,
                                    Optional.of(documentType)
                            ).get();
                            System.out.println("Documento salvataggio in corso " + document.getFilename());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });

                    threads.add(thread);
                    thread.start();
                }
            }

            // Attendi il completamento di tutti i thread
            boolean completed = latch.await(5L, TimeUnit.MINUTES);
            if (!completed) {
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                        .body("Timeout durante l'elaborazione dei file");
            }

            return ResponseEntity.ok().build();


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Errore durante l'elaborazione dei file: " + e.getMessage());
        }
    }





    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .badRequest()
                .body("Error processing request: " + e.getMessage());
    }

    @PostMapping("/api/document/{id}/tags")
    @PreAuthorize("@documentPermission.canWrite(@documentService.findDocumentById(#id), principal)")
    public ResponseEntity<?> addTag(@PathVariable Long id,
                                    @RequestBody DocumentTagDTO tagDTO,
                                    Authentication authentication) {
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        document.addTag(tagDTO.getTag());
        documentService.save(document);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/document/{id}")
    @PreAuthorize("@documentPermission.canWrite(@documentService.findDocumentById(#id), principal)")
    public ResponseEntity<?> deleteDoc(@PathVariable Long id, Authentication authentication) {
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        documentRepository.delete(document);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/api/document/{id}/tags/{tag}")
    @PreAuthorize("@documentPermission.canWrite(@documentService.findDocumentById(#id), principal)")
    public ResponseEntity<?> removeTag(@PathVariable Long id, @PathVariable String tag, Authentication authentication) {
        LoggedUser user = (LoggedUser) authentication.getPrincipal();
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        document.removeTag(tag);
        documentService.save(document);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/document/{id}/tags")
    @PreAuthorize("@documentPermission.canRead(@documentService.findDocumentById(#id), principal)")
    public ResponseEntity<Set<String>> getTags(@PathVariable Long id, Authentication authentication) {
        LoggedUser user = (LoggedUser) authentication.getPrincipal();
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                document.getTags() != null ? document.getTags() : new HashSet<>()
        );
    }

    @PutMapping("/api/document/{id}/description")
    @PreAuthorize("@documentPermission.canWrite(@documentService.findDocumentById(#id), principal)")
    public ResponseEntity<?> updateDescription(@PathVariable Long id,
                                               @RequestBody String description,
                                               Authentication authentication) {
        Document document = documentService.findDocumentById(id);

        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        document.setDescription(description);
        documentService.save(document);

        return ResponseEntity.ok().build();
    }

}
