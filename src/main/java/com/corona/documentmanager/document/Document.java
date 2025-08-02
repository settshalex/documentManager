package com.corona.documentmanager.document;

import com.corona.documentmanager.DocumentShare.DocumentShare;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @Column(name = "unique_id", nullable = false, length = 24)
    private String uniqueId;

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
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Getter
    @Setter
    @Column(name = "data")
    private byte[] data;

    @Setter
    @Lob
    @Column(name = "text")
    @Type(type = "org.hibernate.type.TextType")
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


    public String getSha256() {
        return sha256;
    }

    public String getText() {
        return text;
    }

    public String getUniqueId() {
        return uniqueId;
    }

}