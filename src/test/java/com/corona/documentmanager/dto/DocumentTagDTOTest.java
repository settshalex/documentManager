package com.corona.documentmanager.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTagDTOTest {

    private DocumentTagDTO documentTagDTO;
    private final String TEST_TAG = "test-tag";

    @BeforeEach
    void setUp() {
        documentTagDTO = new DocumentTagDTO();
        documentTagDTO.setTag(TEST_TAG);
    }

    @Test
    void getTag() {
        assertEquals(TEST_TAG, documentTagDTO.getTag());
    }

    @Test
    void setTag() {
        String newTag = "new-tag";
        documentTagDTO.setTag(newTag);
        assertEquals(newTag, documentTagDTO.getTag());
    }

    @Test
    void testEquals() {
        DocumentTagDTO dto1 = new DocumentTagDTO();
        DocumentTagDTO dto2 = new DocumentTagDTO();
        dto1.setTag(TEST_TAG);
        dto2.setTag(TEST_TAG);
        assertEquals(dto1, dto2);
    }

    @Test
    void canEqual() {
        DocumentTagDTO dto1 = new DocumentTagDTO();
        DocumentTagDTO dto2 = new DocumentTagDTO();
        assertTrue(dto1.canEqual(dto2));
    }

    @Test
    void testHashCode() {
        DocumentTagDTO dto1 = new DocumentTagDTO();
        DocumentTagDTO dto2 = new DocumentTagDTO();
        dto1.setTag(TEST_TAG);
        dto2.setTag(TEST_TAG);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "DocumentTagDTO(tag=" + TEST_TAG + ")";
        assertEquals(expectedString, documentTagDTO.toString());
    }
}