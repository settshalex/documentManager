package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DocumentShareTest {

    private DocumentShare documentShare;
    private Document document;
    private User sharedWithUser;
    private User sharedByUser;
    private LocalDateTime sharedAt;

    @BeforeEach
    void setUp() {
        document = new Document();
        sharedWithUser = new User();
        sharedByUser = new User();
        sharedAt = LocalDateTime.now();
        documentShare = new DocumentShare(document, sharedWithUser, sharedByUser, DocumentShare.SharePermission.READ);
        documentShare.setId(1L);
        documentShare.setSharedAt(Instant.from(sharedAt));
    }

    @Test
    void getId() {
        assertEquals(1L, documentShare.getId());
    }

    @Test
    void getDocument() {
        assertEquals(document, documentShare.getDocument());
    }

    @Test
    void getSharedWithUser() {
        assertEquals(sharedWithUser, documentShare.getSharedWithUser());
    }

    @Test
    void getSharedByUser() {
        assertEquals(sharedByUser, documentShare.getSharedByUser());
    }

    @Test
    void getSharedAt() {
        assertEquals(sharedAt, documentShare.getSharedAt());
    }

    @Test
    void getPermission() {
        assertEquals(DocumentShare.SharePermission.READ, documentShare.getPermission());
    }

    @Test
    void setId() {
        documentShare.setId(2L);
        assertEquals(2L, documentShare.getId());
    }

    @Test
    void setDocument() {
        Document newDocument = new Document();
        documentShare.setDocument(newDocument);
        assertEquals(newDocument, documentShare.getDocument());
    }

    @Test
    void setSharedWithUser() {
        User newUser = new User();
        documentShare.setSharedWithUser(newUser);
        assertEquals(newUser, documentShare.getSharedWithUser());
    }

    @Test
    void setSharedByUser() {
        User newUser = new User();
        documentShare.setSharedByUser(newUser);
        assertEquals(newUser, documentShare.getSharedByUser());
    }

    @Test
    void setSharedAt() {
        LocalDateTime newDateTime = LocalDateTime.now();
        documentShare.setSharedAt(Instant.from(newDateTime));
        assertEquals(newDateTime, documentShare.getSharedAt());
    }

    @Test
    void setPermission() {
        documentShare.setPermission(DocumentShare.SharePermission.WRITE);
        assertEquals(DocumentShare.SharePermission.WRITE, documentShare.getPermission());
    }
}