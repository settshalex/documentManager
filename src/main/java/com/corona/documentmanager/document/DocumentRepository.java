package com.corona.documentmanager.document;

import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    Document findDocumentByTitle(String title);
    List<Document> findByCreatedBy(User creator);
    @Query("SELECT d FROM Document d " +
            "JOIN DocumentShare s ON s.document = d " +
            "WHERE s.sharedWithUser = :user")
    List<Document> findSharedWithUser(@Param("user") User user);

}