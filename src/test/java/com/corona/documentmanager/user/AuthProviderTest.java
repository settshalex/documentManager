package com.corona.documentmanager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthProviderTest {

    @InjectMocks
    private AuthProvider authProvider; // le dipendenze mock vengono iniettate qui

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private LoggedUser principal() {
        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("{noop}encoded") // valore qualsiasi; sarà usato solo dal mock di passwordEncoder
                .role("USER")
                .build();
        return new LoggedUser(u);
    }

    @Test
    void supports_UsernamePasswordToken_ReturnsTrue() {
        assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void authenticate_Success_ReturnsAuthenticatedToken() {
        // Arrange
        LoggedUser user = principal();
        String rawPassword = "pwd";

        when(userService.loadUserByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(true);

        // principal può essere lo username oppure il LoggedUser stesso.
        // Qui uso lo username perché AuthProvider legge authentication.getName()
        Authentication authentication = new UsernamePasswordAuthenticationToken("testuser", rawPassword);

        // Act
        Authentication result = authProvider.authenticate(authentication);

        // Assert
        assertNotNull(result);
        assertTrue(result.isAuthenticated());
        assertEquals(user, result.getPrincipal());
    }

    @Test
    void authenticate_BadCredentials_Throws() {
        // Arrange
        LoggedUser user = principal();
        String rawPassword = "wrong";

        when(userService.loadUserByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken("testuser", rawPassword);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authProvider.authenticate(authentication));
    }
}
