package com.corona.documentmanager.exception;

public class DocumentAlreadySharedException extends RuntimeException {
    public DocumentAlreadySharedException(String message) {
        super(message);
    }
}