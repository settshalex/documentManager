package com.corona.documentmanager.document;

import com.corona.documentmanager.File.FileFactory;
import com.corona.documentmanager.File.FileManager;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.exception.DocumentNotFoundException;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private FileFactory fileFactory;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private FileManager fileManager;

    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        documentService = new DocumentService(fileFactory, documentRepository);
    }

    @Test
    void save() {
        Document document = new Document();
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        Document savedDocument = documentService.save(document);

        assertNotNull(savedDocument);
        verify(documentRepository).save(document);
    }

    @Test
    void processFileAsync() throws Exception {
        User owner = new User();
        owner.setUsername("owner");
        Document document = new Document();
        LoggedUser user = new LoggedUser(owner);
        DocumentType docType = new DocumentType();

        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(fileFactory.getFileManager(anyString())).thenReturn(fileManager);
        when(fileManager.createNewDocument(any(), any(), any(), any(), any(), any())).thenReturn(document);
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        CompletableFuture<Document> future = documentService.processFileAsync(
                multipartFile, "title", "description", user, Optional.of(docType));

        Document result = future.get();
        assertNotNull(result);
        verify(documentRepository).save(any(Document.class));
    }

    @Test
    void findDocumentsByUser() {
        User user = new User();
        List<Document> documents = Arrays.asList(new Document(), new Document());

        when(documentRepository.findByCreatedBy(user)).thenReturn(documents);

        List<Document> result = documentService.findDocumentsByUser(user);

        assertEquals(2, result.size());
        verify(documentRepository).findByCreatedBy(user);
    }

    @Test
    void findDocumentById() {
        Document document = new Document();
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        Document result = documentService.findDocumentById(1L);

        assertNotNull(result);
        verify(documentRepository).findById(1L);
    }

    @Test
    void findDocumentById_ThrowsException() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class, () -> documentService.findDocumentById(1L));
    }

    @Test
    void findDocumentsSharedWithUser() {
        User user = new User();
        List<Document> documents = Arrays.asList(new Document(), new Document());

        when(documentRepository.findSharedWithUser(user)).thenReturn(documents);

        List<Document> result = documentService.findDocumentsSharedWithUser(user);

        assertEquals(2, result.size());
        verify(documentRepository).findSharedWithUser(user);
    }
}