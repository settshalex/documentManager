package com.corona.documentmanager.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageDocumentTest {

    ImageDocument imageDocument;
    @BeforeEach
    void setUp() {
        imageDocument = new ImageDocument();
    }
    @Test
    void createNewDocument() {
        ImageDocument imageDocumentNew = new ImageDocument();
        assertNotNull(imageDocumentNew);
        assertEquals(ImageDocument.class, imageDocumentNew.getClass());
        assertNull(imageDocumentNew.language());
    }

    @Test
    void isDocument() {
        assertFalse(imageDocument.isDocument());
    }

    @Test
    void language() {
        assertNull(imageDocument.language());
    }
}