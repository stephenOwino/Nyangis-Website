package com.stephenowinoh.Avante_garde_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // Handle IllegalArgumentException (e.g., from service validation)
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid request: " + ex.getMessage());
        }

        // Handle IOException (e.g., from file operations in /upload or /uploads)
        @ExceptionHandler(IOException.class)
        public ResponseEntity<String> handleIOException(IOException ex) {
                System.err.println("File operation error: " + ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to process file: " + ex.getMessage());
        }

        // Handle MultipartException (e.g., invalid or missing file in multipart request)
        @ExceptionHandler(MultipartException.class)
        public ResponseEntity<String> handleMultipartException(MultipartException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid file upload: " + ex.getMessage());
        }

        // Handle database-specific exceptions
        @ExceptionHandler(org.springframework.dao.InvalidDataAccessResourceUsageException.class)
        public ResponseEntity<String> handleDatabaseException(Exception ex) {
                System.err.println("Database error: " + ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Internal Server Error: Database issue");
        }

        // Fallback for unhandled exceptions
        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGeneralException(Exception ex) {
                System.err.println("Unexpected error: " + ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Internal Server Error: An unexpected error occurred");
        }
}
