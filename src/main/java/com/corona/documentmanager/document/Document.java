package com.corona.documentmanager.document;

import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.documentVersioning.DocumentVersioning;
import com.corona.documentmanager.user.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "unique_id", nullable = false, length = 24)
    private String uniqueId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;

    @Column(name = "description")
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "data")
    private byte[] data;

    @Lob
    @Column(name = "text")
    @Type(type = "org.hibernate.type.TextType")
    private String text;


    @Column(name = "sha256", length = 64)
    private String sha256;

    @OneToMany(mappedBy = "document")
    private Set<DocumentVersioning> documentVersionings = new LinkedHashSet<>();

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "filename")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Set<DocumentVersioning> getDocumentVersionings() {
        return documentVersionings;
    }

    public void setDocumentVersionings(Set<DocumentVersioning> documentVersionings) {
        this.documentVersionings = documentVersionings;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

/*
  TODO [JPA Buddy] create field to map the 'tags' column
   Available actions: Define target Java type | Uncomment as is | Remove column mapping
  @Column(name = "tags", columnDefinition = "text[]")
  private java.lang.Object tags;
*/
}