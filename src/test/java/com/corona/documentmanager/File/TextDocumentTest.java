package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TextDocumentTest {

    TextDocument textDocument;

    @BeforeEach
    void setUp() {
        textDocument = new TextDocument();
    }

    @Test
    void createNewDocument() throws IOException {
        MockMultipartFile textFile = new MockMultipartFile(
                "file",
                "test-audio.txt",
                "text/plain",
                "test file content".getBytes()
        );
        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        LoggedUser principal= new LoggedUser(u);
        DocumentType docType = new DocumentType();
        TextDocument textDocumentNew = new TextDocument();
        Document doc = textDocumentNew.createNewDocument(textFile, principal, "titolo", "descrizione","text/plain", Optional.of(docType));
        assertNotNull(textDocumentNew);
        assertEquals(TextDocument.class, textDocumentNew.getClass());
        assertEquals("text/plain", doc.getMimeType());
        assertEquals("titolo", doc.getTitle());
        List<String> parole = Arrays.asList(
                "file",
                "test",
                "content"
        );
        assertArrayEquals(parole.toArray(), doc.getTags().toArray());
    }

    @Test
    void isDocument() {
        textDocument = new TextDocument();
        assertTrue(textDocument.isDocument());
    }

    @Test
    void language() {
        assertEquals("it", textDocument.language());
    }
}