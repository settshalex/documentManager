package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.exception.DocumentAlreadySharedException;
import com.corona.documentmanager.exception.DocumentNotFoundException;
import com.corona.documentmanager.exception.UnauthorizedAccessException;
import com.corona.documentmanager.exception.UserNotFoundException;
import com.corona.documentmanager.document.DocumentService;
import com.corona.documentmanager.user.LoggedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> shareDocument(
            @PathVariable Long id,
            @RequestParam String username,
            @RequestParam DocumentShare.SharePermission permission,
            @AuthenticationPrincipal LoggedUser currentUser) {

        try {
            DocumentShare share = shareService.shareDocument(id, username, permission, currentUser);
            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Documento condiviso con successo",
                    "shareId", share.getId()
            ));

        } catch (DocumentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));

        } catch (UserNotFoundException | DocumentAlreadySharedException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));

        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Si è verificato un errore interno " + e.getMessage()));
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