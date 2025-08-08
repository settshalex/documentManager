package com.corona.documentmanager.user;

import com.corona.documentmanager.document.Document;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        User user = User.builder()
                .id(1L)
                .build();
        assertEquals(1L, user.getId());
    }

    @Test
    void getUsername() {
        User user = User.builder()
                .username("test@test.com")
                .build();
        assertEquals("test@test.com", user.getUsername());
    }

    @Test
    void getPassword() {
        User user = User.builder()
                .password("password123")
                .build();
        assertEquals("password123", user.getPassword());
    }

    @Test
    void getRole() {
        User user = User.builder()
                .role("USER")
                .build();
        assertEquals("USER", user.getRole());
    }

    @Test
    void getDocuments() {
        Set<Document> documents = new LinkedHashSet<>();
        User user = User.builder()
                .documents(documents)
                .build();
        assertEquals(documents, user.getDocuments());
    }

    @Test
    void setId() {
        User user = new User();
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void setUsername() {
        User user = new User();
        user.setUsername("test@test.com");
        assertEquals("test@test.com", user.getUsername());
    }

    @Test
    void setPassword() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void setRole() {
        User user = new User();
        user.setRole("USER");
        assertEquals("USER", user.getRole());
    }

    @Test
    void setDocuments() {
        User user = new User();
        Set<Document> documents = new LinkedHashSet<>();
        user.setDocuments(documents);
        assertEquals(documents, user.getDocuments());
    }

    @Test
    void builder() {
        Set<Document> documents = new LinkedHashSet<>();
        User user = User.builder()
                .id(1L)
                .username("test@test.com")
                .password("password123")
                .role("USER")
                .documents(documents)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals(documents, user.getDocuments());
    }
}