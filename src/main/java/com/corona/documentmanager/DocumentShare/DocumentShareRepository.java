package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentShareRepository extends JpaRepository<DocumentShare, Long> {
    List<DocumentShare> findByDocument(Document document);
    List<DocumentShare> findBySharedWithUser(User user);
    boolean existsByDocumentAndSharedWithUser(Document document, User user);
}
