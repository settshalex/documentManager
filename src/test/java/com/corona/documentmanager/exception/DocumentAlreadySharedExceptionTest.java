package com.corona.documentmanager.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DocumentAlreadySharedExceptionTest {

    @Test
    void constructorWithMessage() {
        String message = "Document already shared";
        DocumentAlreadySharedException exception = new DocumentAlreadySharedException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause() {
        String message = "Document already shared";
        Throwable cause = new RuntimeException("Test cause");
        DocumentAlreadySharedException exception = new DocumentAlreadySharedException(message);
        exception.initCause(cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}