package com.corona.documentmanager.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShareNotFoundExceptionTest {
    @Test
    void constructorWithMessage() {
        String message = "Document already shared";
        ShareNotFoundException exception = new ShareNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause() {
        String message = "Document already shared";
        Throwable cause = new RuntimeException("Test cause");
        ShareNotFoundException exception = new ShareNotFoundException(message);
        exception.initCause(cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}