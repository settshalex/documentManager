package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.service.DocumentService;
import com.corona.documentmanager.service.DocumentShareService;
import com.corona.documentmanager.user.LoggedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DocumentShareController {

    private final DocumentShareService shareService;
    private final DocumentService documentService;

    @GetMapping("/documents/{id}/share")
    public String showShareForm(@PathVariable Long id,
                                @AuthenticationPrincipal LoggedUser currentUser,
                                Model model) {
        Document document = documentService.findDocumentById(id);
        List<DocumentShare> shares = shareService.getDocumentShares(id, currentUser);

        model.addAttribute("document", document);
        model.addAttribute("shares", shares);
        return "share";
    }

    @PostMapping("/api/documents/{id}/share")
    @ResponseBody
    public ResponseEntity<?> shareDocument(@PathVariable Long id,
                                           @RequestParam String username,
                                           @RequestParam DocumentShare.SharePermission permission,
                                           @AuthenticationPrincipal LoggedUser currentUser) {
        try {
            DocumentShare share = shareService.shareDocument(id, username, permission, currentUser);
            return ResponseEntity.ok(share);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/shares/{shareId}")
    @ResponseBody
    public ResponseEntity<?> removeShare(@PathVariable Long shareId,
                                         @AuthenticationPrincipal LoggedUser currentUser) {
        try {
            shareService.removeShare(shareId, currentUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}