package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import com.google.common.hash.Hashing;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CommonFile {

    public Document prepareNewDocument(MultipartFile file,  LoggedUser customUser, String title, String description, String mime_type, Optional<DocumentType> docType) throws IOException {
        String fileName = file.getOriginalFilename();
        String sha256hex = Hashing.sha256()
                .hashString(file.toString(), StandardCharsets.UTF_8)
                .toString();
        Document newDocument = new Document();
        newDocument.setTitle(title);
        newDocument.setFilename(fileName);
        newDocument.setSha256(sha256hex);
        System.out.println("customUser.user");
        System.out.println("=====>" + customUser.user);
        System.out.println("customUser.user");
        newDocument.setCreatedBy(customUser.user);
        docType.ifPresent(newDocument::setDocumentType);
        newDocument.setDescription(description);
        newDocument.setMimeType(mime_type);
        newDocument.setData(file.getBytes());
        return newDocument;
    }
}
