package com.corona.documentmanager.config;

import com.corona.documentmanager.File.FileFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileManagerFactoryConfigTest {

    @Autowired
    private FileFactory fileFactory;

    @Test
    void fileFactory() {
        assertNotNull(fileFactory, "FileFactory should be created");
        assertTrue(fileFactory.getClass().getName().contains("FileFactory"),
                "Bean should be an instance of FileFactory");
    }
}