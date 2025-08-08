package com.corona.documentmanager.documentType;

import com.corona.documentmanager.document.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTypeTest {

    private DocumentType documentType;
    private Set<Document> documents;

    @BeforeEach
    void setUp() {
        documentType = new DocumentType();
        documents = new HashSet<>();
        documents.add(new Document());
    }

    @Test
    void getDocuments() {
        documentType.setDocuments(documents);
        assertEquals(documents, documentType.getDocuments());
    }

    @Test
    void setDocuments() {
        documentType.setDocuments(documents);
        assertEquals(documents, documentType.getDocuments());
    }

    @Test
    void getType() {
        String type = "PDF";
        documentType.setType(type);
        assertEquals(type, documentType.getType());
    }

    @Test
    void setType() {
        String type = "PDF";
        documentType.setType(type);
        assertEquals(type, documentType.getType());
    }

    @Test
    void getId() {
        Long id = 1L;
        documentType.setId(id);
        assertEquals(id, documentType.getId());
    }

    @Test
    void setId() {
        Long id = 1L;
        documentType.setId(id);
        assertEquals(id, documentType.getId());
    }
}