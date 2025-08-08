package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.document.DocumentService;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentShareControllerTest {

    private DocumentShareController controller;
    private DocumentShareService shareService;
    private Model model;
    private RedirectAttributes redirectAttributes;
    private LoggedUser loggedUser;
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        shareService = mock(DocumentShareService.class);
        documentService = mock(DocumentService.class);
        model = mock(Model.class);
        redirectAttributes = mock(RedirectAttributes.class);
        loggedUser = mock(LoggedUser.class);
        controller = new DocumentShareController(shareService, documentService);
    }

    @Test
    void showShareForm() {
        Long documentId = 1L;
        when(shareService.getDocumentShares(documentId, loggedUser))
                .thenReturn(java.util.Collections.emptyList());

        String viewName = controller.showShareForm(documentId, loggedUser, model);

        assertEquals("share", viewName);
        verify(model).addAttribute(eq("shares"), anyList());
    }

//    @Test
//    void shareDocument() {
//        Long documentId = 1L;
//        String username = "testUser";
//        DocumentShare.SharePermission permission = DocumentShare.SharePermission.READ;
//        DocumentShare share = new DocumentShare();
//
//        when(shareService.shareDocument(documentId, username, permission, loggedUser))
//                .thenReturn(share);
//
//        String redirectUrl = String.valueOf(controller.shareDocument(documentId, username,
//                permission, loggedUser));
//
//        assertEquals("redirect:/documents/" + documentId + "/share", redirectUrl);
//    }

    @Test
    void removeShare() {
        Long shareId = 1L;
        Long documentId = 1L;

        String redirectUrl = String.valueOf(controller.removeShare(shareId, loggedUser));

        assertEquals("<200 OK OK,[]>", redirectUrl);
        verify(shareService).removeShare(shareId, loggedUser);
    }
}