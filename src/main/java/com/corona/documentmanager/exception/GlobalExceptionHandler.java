package com.corona.documentmanager.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<?> handleDocumentNotFound(DocumentNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DocumentAlreadySharedException.class)
    public ResponseEntity<?> handleDocumentAlreadyShared(DocumentAlreadySharedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ShareNotFoundException.class)
    public ResponseEntity<?> handleShareNotFound(ShareNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}