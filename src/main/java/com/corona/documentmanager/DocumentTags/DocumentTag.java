package com.corona.documentmanager.DocumentTags;

import com.corona.documentmanager.document.Document;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String tag;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
