package com.corona.documentmanager.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextDocumentTest {

    TextDocument textDocument;

    @BeforeEach
    void setUp() {
        textDocument = new TextDocument();
    }

    @Test
    void createNewDocument() {
        TextDocument textDocumentNew = new TextDocument();
        assertNotNull(textDocumentNew);
        assertEquals(TextDocument.class, textDocumentNew.getClass());
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