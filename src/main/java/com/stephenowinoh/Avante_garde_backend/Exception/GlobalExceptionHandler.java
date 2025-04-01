package com.stephenowinoh.Avante_garde_backend.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        // Standard error response structure
        private Map<String, String> createErrorResponse(String message) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", message);
                return errorResponse;
        }

        // Handle IllegalArgumentException (e.g., from service validation)
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
                logger.error("Illegal argument error: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Invalid request: " + ex.getMessage()));
        }

        // Handle IOException (e.g., from file operations in /upload or /uploads)
        @ExceptionHandler(IOException.class)
        public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
                logger.error("File operation error: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(createErrorResponse("Failed to process file: " + ex.getMessage()));
        }

        // Handle MultipartException (e.g., invalid or missing file in multipart request)
        @ExceptionHandler(MultipartException.class)
        public ResponseEntity<Map<String, String>> handleMultipartException(MultipartException ex) {
                logger.error("Multipart error: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Invalid file upload: " + ex.getMessage()));
        }

        // Handle EntityNotFoundException (e.g., product not found)
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
                logger.error("Entity not found: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Resource not found: " + ex.getMessage()));
        }

        // Handle MethodArgumentNotValidException (e.g., validation errors on request body)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                }
                logger.error("Validation error: {}", errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Validation failed: " + errors.toString()));
        }

        // Handle database-specific exceptions
        @ExceptionHandler(org.springframework.dao.InvalidDataAccessResourceUsageException.class)
        public ResponseEntity<Map<String, String>> handleDatabaseException(org.springframework.dao.InvalidDataAccessResourceUsageException ex) {
                logger.error("Database error: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(createErrorResponse("Internal Server Error: Database issue"));
        }

        // Fallback for unhandled exceptions
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
                logger.error("Unexpected error: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(createErrorResponse("Internal Server Error: An unexpected error occurred"));
        }
}
