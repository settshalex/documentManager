package com.corona.documentmanager.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedAccessExceptionTest {
    @Test
    void constructorWithMessage() {
        String message = "Document already shared";
        UnauthorizedAccessException exception = new UnauthorizedAccessException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause() {
        String message = "Document already shared";
        Throwable cause = new RuntimeException("Test cause");
        UnauthorizedAccessException exception = new UnauthorizedAccessException(message);
        exception.initCause(cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}