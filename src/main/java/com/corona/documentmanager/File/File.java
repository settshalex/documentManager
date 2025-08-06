package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

public interface File {
    Document createNewDocument(MultipartFile file, LoggedUser customUser, String title, String description, String mime_type, Optional<DocumentType> docType) throws IOException;
    boolean isDocument();
    String language();


}
