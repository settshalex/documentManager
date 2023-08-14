package com.corona.documentmanager.user;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentComments.DocumentComment;
import com.corona.documentmanager.documentVersioning.DocumentVersioning;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 200)
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @OneToMany(mappedBy = "createdBy")
    private Set<DocumentComment> documentComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Document> documents = new LinkedHashSet<>();

    @Column(name = "role", length = 30)
    private String role;

    @OneToMany(mappedBy = "createdBy")
    private Set<DocumentVersioning> documentVersionings = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*
  TODO [JPA Buddy] create field to map the 'preferences' column
   Available actions: Define target Java type | Uncomment as is | Remove column mapping
  @Column(name = "preferences", columnDefinition = "jsonb")
  private Object preferences;
*/
}
