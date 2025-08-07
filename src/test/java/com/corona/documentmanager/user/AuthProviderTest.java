package com.corona.documentmanager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthProviderTest {

    private AuthProvider authProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authProvider = new AuthProvider();
    }

    @Test
    void authenticate_ValidCredentials_ReturnsAuthenticationToken() {
        // Arrange
        String username = "testUser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        // Act
        Authentication result = authProvider.authenticate(authentication);

        // Assert
        assertNotNull(result);
        assertTrue(result.isAuthenticated());
        assertEquals(username, result.getName());
    }

    @Test
    void authenticate_InvalidCredentials_ThrowsException() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authProvider.authenticate(authentication));
    }

    @Test
    void supports_UsernamePasswordToken_ReturnsTrue() {
        // Act & Assert
        assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void supports_OtherAuthenticationType_ReturnsFalse() {
        // Act & Assert
        assertFalse(authProvider.supports(Authentication.class));
    }
}