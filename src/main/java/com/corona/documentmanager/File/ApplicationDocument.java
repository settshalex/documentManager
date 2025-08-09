package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Component
public class ApplicationDocument extends CommonFile implements File, SupportsMime {
    /**
     * @param file
     * @return
     */
    @Override
    public Document createNewDocument(MultipartFile file, LoggedUser customUser, String title, String description, String mime_type, Optional<DocumentType> docType) throws IOException {
        return super.prepareNewDocument(file, customUser, title, description, mime_type, docType);
    }

    /**
     * @return
     */
    @Override
    public boolean isDocument() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String language() {
        return null;
    }

    @Override
    public boolean supports(String mime) {
        return mime != null && mime.startsWith("application/");
    }

}
