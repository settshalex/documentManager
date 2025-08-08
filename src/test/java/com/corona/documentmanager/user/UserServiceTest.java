package com.corona.documentmanager.user;

import com.corona.documentmanager.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username("test@test.com")
                .password("password")
                .role("USER")
                .build();
    }

    @Test
    void list() {
        List<User> expectedUsers = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.list();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll();
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByUsername(testUser.getUsername());

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getUserByUsername(testUser.getUsername());

        assertEquals(testUser, result.get());
    }

    @Test
    void getUserByUsername_ThrowsException() {
        String username = "nonexistent@test.com";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        Optional<User> notFound = userService.getUserByUsername(username);
        assertEquals(Optional.empty(), notFound);
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        var userDetails = userService.loadUserByUsername(testUser.getUsername());

        assertEquals(testUser.getUsername(), userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_ThrowsException() {
        String username = "nonexistent@test.com";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(username));
    }

    @Test
    void createUser() {
        when(userRepository.save(testUser)).thenReturn(testUser);
        userService.createUser(testUser);
        verify(userRepository).save(testUser);
    }

}