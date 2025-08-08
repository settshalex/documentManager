package com.corona.documentmanager.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {
    @Test
    void constructorWithMessage() {
        String message = "Document already shared";
        UserNotFoundException exception = new UserNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause() {
        String message = "Document already shared";
        Throwable cause = new RuntimeException("Test cause");
        UserNotFoundException exception = new UserNotFoundException(message);
        exception.initCause(cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}