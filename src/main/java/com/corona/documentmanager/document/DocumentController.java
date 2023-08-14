package com.corona.documentmanager.document;
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
import java.util.Optional;

@RestController
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentTypeRepository documentTypeRepository;

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
            File fileManager = fileFactory.getFile(mime_type);
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
}
