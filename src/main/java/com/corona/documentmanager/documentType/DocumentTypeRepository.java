package com.corona.documentmanager.documentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    @Transactional
    Optional<DocumentType> findById(Long id);
    @Transactional
    Optional<DocumentType> findByType(String type);
}