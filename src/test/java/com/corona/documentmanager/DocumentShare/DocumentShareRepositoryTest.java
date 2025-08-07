package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocumentShareRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentShareRepository repository;

    private Document document;
    private User owner;
    private User sharedUser;
    private DocumentShare share;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setUsername("owner");
        entityManager.persist(owner);

        sharedUser = new User();
        sharedUser.setUsername("shared");
        entityManager.persist(sharedUser);

        document = new Document();
        document.setCreatedBy(owner);
        document.setFilename("test.txt");
        entityManager.persist(document);

        share = new DocumentShare(document, sharedUser, owner, DocumentShare.SharePermission.READ);
        entityManager.persist(share);
        entityManager.flush();
    }

    @Test
    void findByDocument() {
        List<DocumentShare> found = repository.findByDocument(document);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getSharedWithUser()).isEqualTo(sharedUser);
    }

    @Test
    void findBySharedWithUser() {
        List<DocumentShare> found = repository.findBySharedWithUser(sharedUser);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getDocument()).isEqualTo(document);
    }

    @Test
    void existsByDocumentAndSharedWithUser() {
        boolean exists = repository.existsByDocumentAndSharedWithUser(document, sharedUser);
        assertThat(exists).isTrue();

        User anotherUser = new User();
        anotherUser.setUsername("another");
        entityManager.persist(anotherUser);

        boolean notExists = repository.existsByDocumentAndSharedWithUser(document, anotherUser);
        assertThat(notExists).isFalse();
    }
}