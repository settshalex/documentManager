package com.corona.documentmanager.File;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationDocumentTest {

    ApplicationDocument applicationDocument;
    @Test
    void createNewDocument() {
        applicationDocument = new ApplicationDocument();
        assertNotNull(applicationDocument);
        assertEquals(applicationDocument.getClass(), ApplicationDocument.class);
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