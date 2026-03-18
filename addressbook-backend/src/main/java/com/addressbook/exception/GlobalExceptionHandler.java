package com.addressbook.exception;

import com.addressbook.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST API.
 * Handles all custom exceptions and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handle ContactNotFoundException.
     */
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleContactNotFoundException(ContactNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(APIResponse.error(ex.getMessage()));
    }
    
    /**
     * Handle DuplicateContactException.
     */
    @ExceptionHandler(DuplicateContactException.class)
    public ResponseEntity<APIResponse<Void>> handleDuplicateContactException(DuplicateContactException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(APIResponse.error(ex.getMessage()));
    }
    
    /**
     * Handle validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.error("Validation failed"));
    }
    
    /**
     * Handle generic exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.error("An error occurred: " + ex.getMessage()));
    }
}
