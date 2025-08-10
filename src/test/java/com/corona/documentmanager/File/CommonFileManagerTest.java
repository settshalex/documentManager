package com.corona.documentmanager.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonFileManagerTest {

    CommonFile commonFile;
    @BeforeEach
    void setUp() {
        commonFile = new CommonFile();
    }
    @Test
    void prepareNewDocument() {
        CommonFile commonFileNew = new CommonFile();
        assertNotNull(commonFileNew);
        assertEquals(CommonFile.class, commonFileNew.getClass());
    }
}