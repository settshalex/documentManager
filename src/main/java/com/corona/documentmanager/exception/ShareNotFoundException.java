package com.corona.documentmanager.exception;

public class ShareNotFoundException extends RuntimeException {
    public ShareNotFoundException(String message) {
        super(message);
    }
}