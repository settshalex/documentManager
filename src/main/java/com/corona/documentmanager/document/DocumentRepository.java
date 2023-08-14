package com.corona.documentmanager.document;

import com.corona.documentmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    Document findDocumentByTitle(String title);
    List<Document> findByCreatedBy(User creator);
}