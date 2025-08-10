package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationDocumentTest {

    ApplicationDocument applicationDocument;
    @Test
    void createNewDocument() throws IOException {

        byte[] pdfBytes = "%PDF-1.4\n%Fake PDF content".getBytes();

        MockMultipartFile mockPdf = new MockMultipartFile(
                "file",                      // nome parametro form
                "test.pdf",                  // nome file originale
                "application/pdf",           // MIME type
                pdfBytes                     // contenuto
        );

        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        LoggedUser principal= new LoggedUser(u);
        DocumentType docType = new DocumentType();
        ApplicationDocument appDocument = new ApplicationDocument();
        Document appDocumentNew = appDocument.createNewDocument(mockPdf, principal, "test", "descrizione", "audio/wav", Optional.of(docType));
        assertNotNull(appDocumentNew);
        assertEquals(Document.class, appDocumentNew.getClass());
        System.out.println(appDocumentNew.getDescription());
    }

    @Test
    void isDocument() {
        applicationDocument = new ApplicationDocument();
        assertFalse(applicationDocument.isDocument());
    }

    @Test
    void language() {
        applicationDocument = new ApplicationDocument();
        assertNull(applicationDocument.language());
    }
}