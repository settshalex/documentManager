package com.corona.documentmanager.documentType;

import com.corona.documentmanager.document.Document;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "document_type")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String type;


    @OneToMany(mappedBy = "documentType")
    private Set<Document> documents = new LinkedHashSet<>();

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//TODO [JPA Buddy] generate columns from DB
}