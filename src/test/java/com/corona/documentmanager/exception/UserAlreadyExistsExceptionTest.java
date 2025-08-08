package com.corona.documentmanager.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistsExceptionTest {
    @Test
    void constructorWithMessage() {
        String message = "Document already shared";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause() {
        String message = "Document already shared";
        Throwable cause = new RuntimeException("Test cause");
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);
        exception.initCause(cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}