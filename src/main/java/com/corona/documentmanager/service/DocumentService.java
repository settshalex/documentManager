package com.corona.documentmanager.service;

import com.corona.documentmanager.File.FileFactory;
import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.document.DocumentRepository;
import com.corona.documentmanager.File.File;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.exception.DocumentNotFoundException;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class DocumentService {

    private final FileFactory fileFactory;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(FileFactory fileFactory, DocumentRepository documentRepository) {
        this.fileFactory = fileFactory;
        this.documentRepository = documentRepository;
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Async("documentProcessingExecutor")
    @Transactional
    @Retryable(
            value = {TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 200))
    public CompletableFuture<Document> processFileAsync(
            MultipartFile file,
            String title,
            String description,
            LoggedUser user,
            Optional<DocumentType> docType) {

        try {
            String mimeType = file.getContentType();
            File fileManager = fileFactory.getFileManager(mimeType);
            Document document = fileManager.createNewDocument(
                    file,
                    user,
                    title,
                    description,
                    mimeType,
                    docType
            );
            document.setLastUpdated(Instant.now());
            System.out.println("Documento salvataggio in corso " + document.getFilename());
            Document savedDocument = documentRepository.save(document);
            System.out.println("Documento salvato con successo");
            return CompletableFuture.completedFuture(savedDocument);

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public List<Document> findDocumentsByUser(User user){
        return documentRepository.findByCreatedBy(user);
    }
    public Document findDocumentById(Long id){
        return documentRepository.findById(id).orElseThrow(()->new DocumentNotFoundException("Documento non trovato"));
    }

    public List<Document> findDocumentsSharedWithUser(User user) {
        return documentRepository.findSharedWithUser(user);
    }
}
