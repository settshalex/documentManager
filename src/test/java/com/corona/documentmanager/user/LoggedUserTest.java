package com.corona.documentmanager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class LoggedUserTest {

    private LoggedUser loggedUser;
    private User user;
    private final Long userId = 1L;
    private final String username = "testUser@gmail.com";
    private final String password = "password123";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);
        loggedUser = new LoggedUser(user);
    }

    @Test
    void getPassword() {
        assertEquals(password, loggedUser.getPassword());
    }

    @Test
    void getUserId() {
        assertEquals(userId, loggedUser.getUserId());
    }

    @Test
    void getUsername() {
        assertEquals(username, loggedUser.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(loggedUser.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(loggedUser.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(loggedUser.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(loggedUser.isEnabled());
    }
}