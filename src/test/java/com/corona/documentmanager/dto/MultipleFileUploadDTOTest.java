package com.corona.documentmanager.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultipleFileUploadDTOTest {

    private MultipleFileUploadDTO dto;

    @BeforeEach
    void setUp() {
        dto = new MultipleFileUploadDTO();
        dto.setTitle("Test Title");
        dto.setDescription("Test Description");
        dto.setDocumentType("Test Type");
    }

    @Test
    void getTitle() {
        assertEquals("Test Title", dto.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("Test Description", dto.getDescription());
    }

    @Test
    void getDocumentType() {
        assertEquals("Test Type", dto.getDocumentType());
    }

    @Test
    void setTitle() {
        dto.setTitle("New Title");
        assertEquals("New Title", dto.getTitle());
    }

    @Test
    void setDescription() {
        dto.setDescription("New Description");
        assertEquals("New Description", dto.getDescription());
    }

    @Test
    void setDocumentType() {
        dto.setDocumentType("New Type");
        assertEquals("New Type", dto.getDocumentType());
    }

    @Test
    void testEquals() {
        MultipleFileUploadDTO dto2 = new MultipleFileUploadDTO();
        dto2.setTitle("Test Title");
        dto2.setDescription("Test Description");
        dto2.setDocumentType("Test Type");

        assertEquals(dto, dto2);
        assertTrue(dto.equals(dto2));
    }

    @Test
    void canEqual() {
        MultipleFileUploadDTO dto2 = new MultipleFileUploadDTO();
        assertTrue(dto.canEqual(dto2));
    }


    @Test
    void testHashCode() {
        MultipleFileUploadDTO dto2 = new MultipleFileUploadDTO();
        dto2.setTitle("Test Title");
        dto2.setDescription("Test Description");
        dto2.setDocumentType("Test Type");

        assertEquals(dto.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "MultipleFileUploadDTO(title=Test Title, description=Test Description, documentType=Test Type)";
        assertEquals(expected, dto.toString());
    }
}