package com.corona.documentmanager.DocumentTags;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentTagRepository extends JpaRepository<DocumentTag, Long> {
    List<DocumentTag> findByTag(String tag);
    List<DocumentTag> findByDocumentId(Long documentId);
}
