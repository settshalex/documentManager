package com.corona.documentmanager.documentVersioning;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentVersioningRepository extends JpaRepository<DocumentVersioning, Long> {
}