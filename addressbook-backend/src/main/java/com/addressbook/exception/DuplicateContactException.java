package com.addressbook.exception;

/**
 * Custom exception for duplicate contact scenarios.
 * UC6: Prevent duplicate contacts
 */
public class DuplicateContactException extends RuntimeException {
    
    public DuplicateContactException(String message) {
        super(message);
    }
    
    public DuplicateContactException(String message, Throwable cause) {
        super(message, cause);
    }
}
