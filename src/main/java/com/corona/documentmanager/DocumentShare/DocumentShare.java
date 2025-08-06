package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.user.User;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "document_shares")
@Getter
@Setter
public class DocumentShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    @NotNull
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with_user_id", nullable = false)
    private User sharedWithUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_by_user_id", nullable = false)
    private User sharedByUser;

    @Column(nullable = false)
    private Instant sharedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SharePermission permission;

    public enum SharePermission {
        READ, WRITE
    }

    // Costruttore di default
    public DocumentShare() {
    }

    // Costruttore con parametri
    public DocumentShare(Document document, User sharedWithUser, User sharedByUser, SharePermission permission) {
        this.document = document;
        this.sharedWithUser = sharedWithUser;
        this.sharedByUser = sharedByUser;
        this.permission = permission;
        this.sharedAt = Instant.now();
    }
}