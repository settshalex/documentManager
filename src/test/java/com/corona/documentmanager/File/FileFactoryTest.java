package com.corona.documentmanager.File;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileFactoryTest {

    @Test
    void getFileManager() {
        FileFactory fileFactory = new FileFactory();
        File a = fileFactory.getFileManager("text/");
        assertNotNull(a);
        assertEquals(a.getClass(), TextDocument.class);
        File b = fileFactory.getFileManager("audio/");
        assertNotNull(b);
        assertEquals(b.getClass(), AudioDocument.class);
        File c = fileFactory.getFileManager("application/");
        assertNotNull(c);
        assertEquals(c.getClass(), ApplicationDocument.class);
        File d = fileFactory.getFileManager("image/");
        assertNotNull(d);
        assertEquals(d.getClass(), ImageDocument.class);
    }
}