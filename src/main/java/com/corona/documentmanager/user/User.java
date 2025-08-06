package com.corona.documentmanager.user;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentComments.DocumentComment;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 200)
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "role", length = 30)
    private String role;

    @OneToMany(mappedBy = "createdBy")
    private Set<DocumentComment> documentComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Document> documents = new LinkedHashSet<>();

}
