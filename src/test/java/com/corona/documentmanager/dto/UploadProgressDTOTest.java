package com.corona.documentmanager.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadProgressDTOTest {

    private UploadProgressDTO uploadProgressDTO;
    private final String TEST_FILE_NAME = "test-file-name";
    private final String TEST_STATUS = "test-status";
    private final String TEST_ERROR = "test-error";
    private final Long TEST_DOCUMENT_ID = 1L;

    @BeforeEach
    void setUp() {
        uploadProgressDTO = new UploadProgressDTO(TEST_FILE_NAME, TEST_STATUS, TEST_ERROR, TEST_DOCUMENT_ID);
        uploadProgressDTO.setFileName(TEST_FILE_NAME);
        uploadProgressDTO.setStatus(TEST_STATUS);
        uploadProgressDTO.setError(TEST_ERROR);
        uploadProgressDTO.setDocumentId(TEST_DOCUMENT_ID);
    }
    @Test
    void getFileName() {
        assertEquals(TEST_FILE_NAME, uploadProgressDTO.getFileName());
    }

    @Test
    void getStatus() {
        assertEquals(TEST_STATUS, uploadProgressDTO.getStatus());
    }

    @Test
    void getError() {
        assertEquals(TEST_ERROR, uploadProgressDTO.getError());
    }

    @Test
    void getDocumentId() {
        assertEquals(TEST_DOCUMENT_ID, uploadProgressDTO.getDocumentId());
    }

    @Test
    void setFileName() {
        String newFileName = "new-file-name";
        uploadProgressDTO.setFileName(newFileName);
        assertEquals(newFileName, uploadProgressDTO.getFileName());
    }

    @Test
    void setStatus() {
        String newStatus = "new-status";
        uploadProgressDTO.setStatus(newStatus);
        assertEquals(newStatus, uploadProgressDTO.getStatus());
    }

    @Test
    void setError() {
        String newError = "new-error";
        uploadProgressDTO.setError(newError);
        assertEquals(newError, uploadProgressDTO.getError());
    }

    @Test
    void setDocumentId() {
        Long newDocumentId = 2L;
        uploadProgressDTO.setDocumentId(newDocumentId);
        assertEquals(newDocumentId, uploadProgressDTO.getDocumentId());
    }
}