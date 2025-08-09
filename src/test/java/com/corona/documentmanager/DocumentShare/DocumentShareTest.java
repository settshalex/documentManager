package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.junit.jupiter.api.Assertions.*;

class DocumentShareTest {

    private DocumentShare documentShare;
    private Document document;
    private User sharedWithUser;
    private User sharedByUser;

    @BeforeEach
    void setUp() {
        document = new Document();
        sharedWithUser = new User();
        sharedByUser = new User();
        documentShare = new DocumentShare(document, sharedWithUser, sharedByUser, DocumentShare.SharePermission.READ);
        documentShare.setId(1L);
        documentShare.setSharedAt(Instant.now());
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
        Instant expected = Instant.parse("2025-08-08T06:07:09.789586Z");
        documentShare.setSharedAt(expected);
        long actualMillis = documentShare.getSharedAt().toEpochMilli();
        long expectedMillis = expected.toEpochMilli();
        assertEquals(expectedMillis, actualMillis);
    }



    @Test
    void setPermission() {
        documentShare.setPermission(DocumentShare.SharePermission.WRITE);
        assertEquals(DocumentShare.SharePermission.WRITE, documentShare.getPermission());
    }
}