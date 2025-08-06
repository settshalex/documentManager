package com.corona.documentmanager.DocumentTags;

import com.corona.documentmanager.document.Document;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;

@Entity
@Table(name = "document_tags")
@Getter
@Setter
public class DocumentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "tag", nullable = false)
    private String tag;

    // Costruttore di default richiesto da JPA
    public DocumentTag() {
    }

    // Costruttore con parametri per facilità d'uso
    public DocumentTag(Document document, String tag) {
        this.document = document;
        this.tag = tag;
    }
}

