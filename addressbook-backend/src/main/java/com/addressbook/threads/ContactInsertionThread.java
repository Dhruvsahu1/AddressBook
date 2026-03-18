package com.addressbook.threads;

import com.addressbook.dto.ContactDTO;
import com.addressbook.service.ContactService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread-safe contact insertion using ExecutorService.
 * UC21: Insert multiple contacts simultaneously using Threads
 */
public class ContactInsertionThread {
    
    private final ContactService contactService;
    private final ExecutorService executorService;
    
    public ContactInsertionThread(ContactService contactService) {
        this.contactService = contactService;
        this.executorService = Executors.newFixedThreadPool(10);
    }
    
    /**
     * Insert multiple contacts asynchronously using CompletableFuture.
     * Thread-safe design - IO operations do not block the main thread.
     */
    public CompletableFuture<List<ContactDTO>> insertContactsAsync(List<ContactDTO> contacts) {
        return CompletableFuture.supplyAsync(() -> {
            return contactService.addMultipleContacts(contacts);
        }, executorService);
    }
    
    /**
     * Insert a single contact asynchronously.
     */
    public CompletableFuture<ContactDTO> insertContactAsync(ContactDTO contact) {
        return CompletableFuture.supplyAsync(() -> {
            return contactService.addContact(contact);
        }, executorService);
    }
    
    /**
     * Shutdown the executor service.
     */
    public void shutdown() {
        executorService.shutdown();
    }
}
