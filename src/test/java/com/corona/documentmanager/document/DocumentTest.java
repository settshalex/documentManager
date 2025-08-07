package com.corona.documentmanager.document;

import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    private Document document;
    private User user;
    private DocumentType documentType;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        documentType = new DocumentType();
        document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setCreatedBy(user);
        document.setDocumentType(documentType);
    }

    @Test
    void getShares_ShouldReturnEmptySet_WhenNoShares() {
        assertTrue(document.getShares().isEmpty());
    }

    @Test
    void onSave_ShouldSetLastUpdated() {
        document.onSave();
        assertNotNull(document.getLastUpdated());
    }

    @Test
    void getTags_ShouldReturnEmptySet_WhenNoTags() {
        assertTrue(document.getTags().isEmpty());
    }

    @Test
    void addTag_ShouldAddTagToSet() {
        String tag = "test-tag";
        document.addTag(tag);
        assertTrue(document.getTags().contains(tag));
    }

    @Test
    void removeTag_ShouldRemoveTagFromSet() {
        String tag = "test-tag";
        document.addTag(tag);
        document.removeTag(tag);
        assertFalse(document.getTags().contains(tag));
    }

    @Test
    void hasTag_ShouldReturnTrue_WhenTagExists() {
        String tag = "test-tag";
        document.addTag(tag);
        assertTrue(document.hasTag(tag));
    }

    @Test
    void getFormattedLastUpdated_ShouldReturnFormattedDate() {
        Instant now = Instant.now();
        document.setLastUpdated(now);
        assertNotNull(document.getFormattedLastUpdated());
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        assertEquals(1L, document.getId());
    }

    @Test
    void getTitle_ShouldReturnCorrectTitle() {
        assertEquals("Test Document", document.getTitle());
    }

    @Test
    void getCreatedBy_ShouldReturnCorrectUser() {
        assertEquals(user, document.getCreatedBy());
    }

    @Test
    void getLastUpdated_ShouldReturnNull_WhenNotSet() {
        assertNull(document.getLastUpdated());
    }

    @Test
    void getDocumentType_ShouldReturnCorrectType() {
        assertEquals(documentType, document.getDocumentType());
    }

    @Test
    void getDescription_ShouldReturnNull_WhenNotSet() {
        assertNull(document.getDescription());
    }

    @Test
    void getData_ShouldReturnNull_WhenNotSet() {
        assertNull(document.getData());
    }

    @Test
    void getText_ShouldReturnNull_WhenNotSet() {
        assertNull(document.getText());
    }

    @Test
    void getMimeType_ShouldReturnNull_WhenNotSet() {
        assertNull(document.getMimeType());
    }

    @Test
    void getFilename_ShouldReturnNull_WhenNotSet() {
        assertNull(document.getFilename());
    }

    @Test
    void setId_ShouldUpdateId() {
        document.setId(2L);
        assertEquals(2L, document.getId());
    }

    @Test
    void setTitle_ShouldUpdateTitle() {
        document.setTitle("New Title");
        assertEquals("New Title", document.getTitle());
    }

    @Test
    void setCreatedBy_ShouldUpdateUser() {
        User newUser = new User();
        newUser.setId(2L);
        document.setCreatedBy(newUser);
        assertEquals(newUser, document.getCreatedBy());
    }

    @Test
    void setLastUpdated_ShouldUpdateDate() {
        Instant now = Instant.now();
        document.setLastUpdated(now);
        assertEquals(now, document.getLastUpdated());
    }

    @Test
    void setDocumentType_ShouldUpdateType() {
        DocumentType newType = new DocumentType();
        document.setDocumentType(newType);
        assertEquals(newType, document.getDocumentType());
    }

    @Test
    void setDescription_ShouldUpdateDescription() {
        document.setDescription("New Description");
        assertEquals("New Description", document.getDescription());
    }

    @Test
    void setData_ShouldUpdateData() {
        byte[] data = "test".getBytes();
        document.setData(data);
        assertArrayEquals(data, document.getData());
    }

    @Test
    void setText_ShouldUpdateText() {
        document.setText("New Text");
        assertEquals("New Text", document.getText());
    }

    @Test
    void setMimeType_ShouldUpdateMimeType() {
        document.setMimeType("application/pdf");
        assertEquals("application/pdf", document.getMimeType());
    }

    @Test
    void setFilename_ShouldUpdateFilename() {
        document.setFilename("test.pdf");
        assertEquals("test.pdf", document.getFilename());
    }
}