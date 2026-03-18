package com.addressbook.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an Address Book that contains multiple contacts.
 * UC5: Support multiple address books using Map<String, AddressBook>
 */
@Data
public class AddressBook {
    
    private String bookId;
    private String bookName;
    private Map<String, ContactPerson> contacts;
    
    public AddressBook() {
        this.contacts = new HashMap<>();
    }
    
    public AddressBook(String bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.contacts = new HashMap<>();
    }
    
    /**
     * Add a contact to the address book.
     */
    public void addContact(ContactPerson contact) {
        String key = contact.getFirstName() + "_" + contact.getLastName();
        contacts.put(key.toLowerCase(), contact);
    }
    
    /**
     * Remove a contact from the address book by name.
     */
    public boolean removeContact(String firstName, String lastName) {
        String key = firstName + "_" + lastName;
        return contacts.remove(key.toLowerCase()) != null;
    }
    
    /**
     * Find a contact by name.
     */
    public ContactPerson findByName(String firstName, String lastName) {
        String key = firstName + "_" + lastName;
        return contacts.get(key.toLowerCase());
    }
    
    /**
     * Get all contacts as a list.
     */
    public List<ContactPerson> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }
    
    /**
     * Get count of contacts.
     */
    public int getCount() {
        return contacts.size();
    }
    
    /**
     * Search contacts by city.
     */
    public List<ContactPerson> searchByCity(String city) {
        return contacts.values().stream()
                .filter(c -> c.getCity() != null && c.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
    
    /**
     * Search contacts by state.
     */
    public List<ContactPerson> searchByState(String state) {
        return contacts.values().stream()
                .filter(c -> c.getState() != null && c.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }
    
    /**
     * Get contacts sorted by name.
     */
    public List<ContactPerson> getSortedByName() {
        return contacts.values().stream()
                .sorted((c1, c2) -> c1.getFullName().compareToIgnoreCase(c2.getFullName()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get contacts sorted by city.
     */
    public List<ContactPerson> getSortedByCity() {
        return contacts.values().stream()
                .sorted((c1, c2) -> {
                    if (c1.getCity() == null) return 1;
                    if (c2.getCity() == null) return -1;
                    return c1.getCity().compareToIgnoreCase(c2.getCity());
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Get contacts sorted by state.
     */
    public List<ContactPerson> getSortedByState() {
        return contacts.values().stream()
                .sorted((c1, c2) -> {
                    if (c1.getState() == null) return 1;
                    if (c2.getState() == null) return -1;
                    return c1.getState().compareToIgnoreCase(c2.getState());
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Get contacts sorted by zip.
     */
    public List<ContactPerson> getSortedByZip() {
        return contacts.values().stream()
                .sorted((c1, c2) -> {
                    if (c1.getZip() == null) return 1;
                    if (c2.getZip() == null) return -1;
                    return c1.getZip().compareTo(c2.getZip());
                })
                .collect(Collectors.toList());
    }
}
