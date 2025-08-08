package com.corona.documentmanager.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleDocumentNotFound() {
        DocumentNotFoundException exception = new DocumentNotFoundException("Document not found");
        ResponseEntity<String> response = (ResponseEntity<String>) exceptionHandler.handleDocumentNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleUnauthorizedAccess() {
        UnauthorizedAccessException exception = new UnauthorizedAccessException("Unauthorized access");
        ResponseEntity<String> response = (ResponseEntity<String>) exceptionHandler.handleUnauthorizedAccess(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void handleUserNotFound() {
        UserNotFoundException exception = new UserNotFoundException("User not found");
        ResponseEntity<String> response = (ResponseEntity<String>) exceptionHandler.handleUserNotFound(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleDocumentAlreadyShared() {
        DocumentAlreadySharedException exception = new DocumentAlreadySharedException("Document already shared");
        ResponseEntity<String> response = (ResponseEntity<String>) exceptionHandler.handleDocumentAlreadyShared(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleShareNotFound() {
        ShareNotFoundException exception = new ShareNotFoundException("Share not found");
        ResponseEntity<String> response = (ResponseEntity<String>) exceptionHandler.handleShareNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}