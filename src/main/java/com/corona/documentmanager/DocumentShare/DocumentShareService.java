package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.exception.*;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import com.corona.documentmanager.user.UserService;
import java.util.List;
import java.util.stream.Collectors;
import com.corona.documentmanager.document.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentShareService {

    private final DocumentShareRepository shareRepository;
    private final DocumentRepository documentRepository;
    private final UserService userService;

    @Transactional
    public DocumentShare shareDocument(Long documentId, String username,
                                       DocumentShare.SharePermission permission,
                                       LoggedUser currentUser) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Documento non trovato"));

        // Verifica che l'utente corrente sia il proprietario
        if (!document.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new UnauthorizedAccessException("Non hai i permessi per condividere questo documento");
        }

        User targetUser = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato: " + username));

        // Verifica che il documento non sia già condiviso con l'utente
        if (shareRepository.existsByDocumentAndSharedWithUser(document, targetUser)) {
            throw new DocumentAlreadySharedException("Il documento è già condiviso con questo utente");
        }

        DocumentShare share = new DocumentShare(document, targetUser, currentUser.user, permission);
        return shareRepository.save(share);
    }

    public List<DocumentShare> getDocumentShares(Long documentId, LoggedUser currentUser) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Documento non trovato"));

        if (!document.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new UnauthorizedAccessException("Non hai i permessi per vedere le condivisioni");
        }

        return shareRepository.findByDocument(document);
    }

    public List<Document> getSharedDocuments(User currentUser) {
        return shareRepository.findBySharedWithUser(currentUser).stream()
                .map(DocumentShare::getDocument)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeShare(Long shareId, LoggedUser currentUser) {
        DocumentShare share = shareRepository.findById(shareId)
                .orElseThrow(() -> new ShareNotFoundException("Condivisione non trovata"));

        if (!share.getDocument().getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new UnauthorizedAccessException("Non hai i permessi per rimuovere questa condivisione");
        }

        shareRepository.delete(share);
    }
}
