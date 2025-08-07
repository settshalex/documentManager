package com.corona.documentmanager.document;

import com.corona.documentmanager.DocumentShare.DocumentShareRepository;
import com.corona.documentmanager.DocumentShare.DocumentShareService;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.documentType.DocumentTypeRepository;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private DocumentShareRepository documentShareRepository;

    private Document testDocument;
    private DocumentType testDocumentType;
    private User testUser;

    @BeforeEach
    void setUp() {
        testDocumentType = new DocumentType();
        testDocumentType.setType("TEST_TYPE");
        entityManager.persist(testDocumentType);
        entityManager.flush();

        testUser = new User();
        testUser.setUsername("test@example.com");
        entityManager.persist(testUser);

        testDocument = new Document();
        testDocument.setTitle("Test Document");
        testDocument.setCreatedBy(testUser);
        testDocument.setDocumentType(testDocumentType);
        entityManager.persist(testDocument);
        entityManager.flush();
    }

    @Test
    void findDocumentByTitle() {
        List<Document> found = documentRepository.findDocumentByTitle("Test Document");
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getTitle()).isEqualTo("Test Document");
    }

    @Test
    void findByCreatedBy() {
        List<Document> found = documentRepository.findByCreatedBy(testUser);
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getCreatedBy()).isEqualTo(testUser);
    }

    @Test
    void findSharedWithUser() {
        User sharedUser = new User();
        sharedUser.setUsername("shared@example.com");
        sharedUser.setPassword("testpsw");
        entityManager.persist(sharedUser);

        documentShareRepository.existsByDocumentAndSharedWithUser(testDocument, sharedUser);
        entityManager.persist(testDocument);
        entityManager.flush();

        List<Document> found = documentRepository.findSharedWithUser(sharedUser);
        assertThat(found).isNotEmpty();
    }
}