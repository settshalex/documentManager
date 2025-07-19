package com.corona.documentmanager.document;
import com.corona.documentmanager.service.DocumentService;
import com.corona.documentmanager.File.File;
import com.corona.documentmanager.File.FileFactory;
import com.corona.documentmanager.File.FileParser;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.documentType.DocumentTypeRepository;
import com.corona.documentmanager.user.LoggedUser;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;

    @Autowired
    public DocumentController(DocumentService documentService,
                              DocumentTypeRepository documentTypeRepository, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentTypeRepository = documentTypeRepository;
        this.documentRepository = documentRepository;
    }


    @GetMapping("/api/document/{id}")
    public String documentDetails(@PathVariable Long id) {

        return "Greetings from Spring Boot!";
    }

    @PostMapping("/api/document/")
    public ResponseEntity<Object> newDocument(Authentication authentication, @RequestPart MultipartFile file, @RequestPart String title, @RequestPart String description) {

        LoggedUser customUser = (LoggedUser)authentication.getPrincipal();
        Long userId = customUser.getUserId();
        System.out.println(userId);
        System.out.println("Title:" + title);
        System.out.println("description:" + title);

        String mime_type = "";
        try {
            InputStream stream = file.getInputStream();      // open the stream
            mime_type = FileParser.parse(stream); // parse the stream

        } catch (IOException | TikaException | SAXException ignored) {

        }

        try {
            FileFactory fileFactory= new FileFactory();
            System.out.println("mime_type");
            System.out.println(mime_type);
            File fileManager = fileFactory.getFileManager(mime_type);
            Optional<DocumentType> docType = documentTypeRepository.findByType("default");
            Document newDocument = fileManager.createNewDocument(file, customUser, title, description, mime_type, docType);
            System.out.println("----------------");
            System.out.println(customUser);
            System.out.println(newDocument.getFilename());
            documentRepository.save(newDocument);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
        return ResponseEntity.ok("File uploaded successfully.");
    }
    @PostMapping("/api/upload/multiple")
    public ResponseEntity<?> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("title") String baseTitle,
            @RequestParam("description") String description,
            @ModelAttribute LoggedUser customUser) {

        try {
            Optional<DocumentType> docType = documentTypeRepository.findByType("default");

            // Crea una lista di CompletableFuture per ogni file
            List<CompletableFuture<Document>> futures = new ArrayList<>();

            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                String title = baseTitle + "_" + (i + 1);

                CompletableFuture<Document> future = documentService.processFileAsync(
                        file,
                        title,
                        description,
                        customUser,
                        docType
                );

                futures.add(future);
            }

            // Attendi il completamento di tutte le operazioni
            CompletableFuture<Void> allOf = CompletableFuture.allOf(
                    futures.toArray(new CompletableFuture[0])
            );

            // Raccogli i risultati
            CompletableFuture<List<Document>> allDocuments = allOf.thenApply(v ->
                    futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList())
            );

            // Gestisci il risultato finale
            List<Document> savedDocuments = allDocuments.join();
            return ResponseEntity.ok(savedDocuments);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing files: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .badRequest()
                .body("Error processing request: " + e.getMessage());
    }

}
