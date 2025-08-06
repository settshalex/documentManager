package com.corona.documentmanager.document;

import com.corona.documentmanager.DocumentShare.DocumentShare;
import com.corona.documentmanager.DocumentTags.DocumentTag;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "documents")
public class Document {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "title", nullable = false)
    private String title;


    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Getter
    @Setter
    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;

    @Getter
    @Setter
    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @Getter
    @Setter
    @Column(name = "data")
    private byte[] data;

    @Getter
    @Setter
    @Lob
    @Column(name = "text")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String text;


    @Setter
    @Column(name = "sha256", length = 64)
    private String sha256;

    @Getter
    @Setter
    @Column(name = "mime_type")
    private String mimeType;

    @Setter
    @Getter
    @Column(name = "filename")
    private String filename;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DocumentShare> shares = new HashSet<>();

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DocumentTag> tags = new HashSet<>();

    public Set<DocumentShare> getShares() {
        return shares != null ? shares : new HashSet<>();
    }

    @PrePersist
    @PreUpdate
    protected void onSave() {
        lastUpdated = Instant.now();
    }


    public Set<String> getTags() {
        return tags.stream()
                .map(DocumentTag::getTag)
                .collect(Collectors.toSet());
    }

    public void addTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return;
        }

        if (!hasTag(tag)) {
            DocumentTag documentTag = new DocumentTag();
            documentTag.setDocument(this);
            documentTag.setTag(tag);
            tags.add(documentTag);
        }
    }

    public void removeTag(String tag) {
        tags.removeIf(documentTag -> documentTag.getTag().equals(tag));
    }

    public boolean hasTag(String tag) {
        return tags.stream()
                .map(DocumentTag::getTag)
                .anyMatch(t -> t.equals(tag));
    }

    public String getFormattedLastUpdated() {
        if (lastUpdated == null) {
            return "";
        }
        return lastUpdated.atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }




}