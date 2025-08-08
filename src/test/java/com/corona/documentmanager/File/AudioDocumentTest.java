package com.corona.documentmanager.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioDocumentTest {

    AudioDocument audioDocument;

    @BeforeEach
    void setUp() {
        audioDocument = new AudioDocument();
    }
    @Test
    void createNewDocument() {
        AudioDocument audioDocumentNew = new AudioDocument();
        assertNotNull(audioDocumentNew);
        assertEquals(AudioDocument.class, audioDocumentNew.getClass());
        assertNull(audioDocumentNew.language());
    }

    @Test
    void isDocument() {
        assertFalse(audioDocument.isDocument());
    }

    @Test
    void language() {
        assertNull(audioDocument.language());
    }
}