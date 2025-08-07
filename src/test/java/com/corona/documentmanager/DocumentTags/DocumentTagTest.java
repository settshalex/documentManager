package com.corona.documentmanager.DocumentTags;

import com.corona.documentmanager.document.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTagTest {

    private DocumentTag documentTag;
    private Document document;
    private String tag;

    @BeforeEach
    void setUp() {
        documentTag = new DocumentTag();
        document = new Document();
        tag = "newTag";
    }

    @Test
    void getId() {
        Long id = 1L;
        documentTag.setId(id);
        assertEquals(id, documentTag.getId());
    }

    @Test
    void getDocument() {
        documentTag.setDocument(document);
        assertEquals(document, documentTag.getDocument());
    }

    @Test
    void getTag() {
        documentTag.setTag(tag);
        assertEquals(tag, documentTag.getTag());
    }

    @Test
    void setId() {
        Long id = 1L;
        documentTag.setId(id);
        assertEquals(id, documentTag.getId());
    }

    @Test
    void setDocument() {
        documentTag.setDocument(document);
        assertEquals(document, documentTag.getDocument());
    }

    @Test
    void setTag() {
        documentTag.setTag(tag);
        assertEquals(tag, documentTag.getTag());
    }
}