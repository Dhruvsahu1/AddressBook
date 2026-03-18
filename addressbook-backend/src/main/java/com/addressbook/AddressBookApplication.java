package com.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Address Book Application.
 * Prints welcome message on startup.
 */
@SpringBootApplication
public class AddressBookApplication {
    
    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        SpringApplication.run(AddressBookApplication.class, args);
    }
}
