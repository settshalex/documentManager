package com.corona.documentmanager.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationDTOTest {

    private UserRegistrationDTO userRegistrationDTO;
    private final String TEST_USERNAME = "test-username@gmail.com";
    private final String TEST_PASSWORD = "test-password";
    private final String TEST_CONFIRM_PASSWORD = "test-password";
    @BeforeEach
    void setUp() {
        userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setPassword(TEST_PASSWORD);
        userRegistrationDTO.setConfirmPassword(TEST_CONFIRM_PASSWORD);
        userRegistrationDTO.setUsername(TEST_USERNAME);
    }
    @Test
    void getUsername() {
        assertEquals(TEST_USERNAME, userRegistrationDTO.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals(TEST_PASSWORD, userRegistrationDTO.getPassword());
    }

    @Test
    void getConfirmPassword() {
        assertEquals(TEST_CONFIRM_PASSWORD, userRegistrationDTO.getConfirmPassword());
    }

    @Test
    void setUsername() {
        String newUsername = "new-username@gmail.com";
        userRegistrationDTO.setUsername(newUsername);
        assertEquals(newUsername, userRegistrationDTO.getUsername());
    }

    @Test
    void setPassword() {
        String newPassword = "new-password";
        userRegistrationDTO.setPassword(newPassword);
        assertEquals(newPassword, userRegistrationDTO.getPassword());
    }

    @Test
    void setConfirmPassword() {
        String newConfirmPassword = "new-password";
        userRegistrationDTO.setConfirmPassword(newConfirmPassword);
        assertEquals(newConfirmPassword, userRegistrationDTO.getConfirmPassword());
    }
}