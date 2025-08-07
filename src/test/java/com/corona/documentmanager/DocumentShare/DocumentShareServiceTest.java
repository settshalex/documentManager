package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.document.DocumentRepository;
import com.corona.documentmanager.exception.*;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import com.corona.documentmanager.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentShareServiceTest {

    @Mock
    private DocumentShareRepository shareRepository;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private UserService userService;

    private DocumentShareService documentShareService;
    private LoggedUser currentUser;
    private User user;
    private Document document;

    @BeforeEach
    void setUp() {
        documentShareService = new DocumentShareService(shareRepository, documentRepository, userService);
        user = new User();
        user.setUsername("testUser");
        currentUser = new LoggedUser(user);
        document = new Document();
        document.setCreatedBy(user);
    }

    @Test
    void shareDocument() {
        User targetUser = new User();
        targetUser.setUsername("targetUser");
        DocumentShare share = new DocumentShare(document, targetUser, user, DocumentShare.SharePermission.READ);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(userService.findByUsername("targetUser")).thenReturn(Optional.of(targetUser));
        when(shareRepository.existsByDocumentAndSharedWithUser(document, targetUser)).thenReturn(false);
        when(shareRepository.save(any(DocumentShare.class))).thenReturn(share);

        DocumentShare result = documentShareService.shareDocument(1L, "targetUser",
                DocumentShare.SharePermission.READ, currentUser);

        assertNotNull(result);
        verify(shareRepository).save(any(DocumentShare.class));
    }

    @Test
    void getDocumentShares() {
        List<DocumentShare> shares = Arrays.asList(new DocumentShare(), new DocumentShare());

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(shareRepository.findByDocument(document)).thenReturn(shares);

        List<DocumentShare> result = documentShareService.getDocumentShares(1L, currentUser);

        assertEquals(2, result.size());
        verify(shareRepository).findByDocument(document);
    }

    @Test
    void getSharedDocuments() {
        List<DocumentShare> shares = Arrays.asList(new DocumentShare(), new DocumentShare());
        when(shareRepository.findBySharedWithUser(user)).thenReturn(shares);

        List<Document> result = documentShareService.getSharedDocuments(user);

        assertNotNull(result);
        verify(shareRepository).findBySharedWithUser(user);
    }

    @Test
    void removeShare() {
        DocumentShare share = new DocumentShare();
        share.setDocument(document);

        when(shareRepository.findById(1L)).thenReturn(Optional.of(share));

        documentShareService.removeShare(1L, currentUser);

        verify(shareRepository).delete(share);
    }
}