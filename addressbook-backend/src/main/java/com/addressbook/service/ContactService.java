package com.addressbook.service;

import com.addressbook.dto.ContactDTO;

import java.util.List;
import java.util.Map;

/**
 * Service interface for Contact operations.
 * Defines all business operations for contact management.
 */
public interface ContactService {
    
    // UC1: Add new contact
    ContactDTO addContact(ContactDTO contactDTO);
    
    // UC2: Edit contact using name
    ContactDTO editContact(String firstName, String lastName, ContactDTO contactDTO);
    
    // UC3: Delete contact using name
    boolean deleteContact(String firstName, String lastName);
    
    // UC4: Add multiple contacts using Collections
    List<ContactDTO> addMultipleContacts(List<ContactDTO> contactDTOs);
    
    // Get all contacts
    List<ContactDTO> getAllContacts();
    
    // Get contact by id
    ContactDTO getContactById(Long id);
    
    // UC7: Search contacts by city or state
    List<ContactDTO> searchByCity(String city);
    List<ContactDTO> searchByState(String state);
    
    // UC8: View persons grouped by city or state
    Map<String, List<ContactDTO>> groupByCity();
    Map<String, List<ContactDTO>> groupByState();
    
    // UC9: Count contacts by city or state
    Map<String, Long> countByCity();
    Map<String, Long> countByState();
    
    // UC10: Sort contacts alphabetically by name
    List<ContactDTO> sortByName();
    
    // UC11: Sort contacts by city, state, or zip
    List<ContactDTO> sortByCity();
    List<ContactDTO> sortByState();
    List<ContactDTO> sortByZip();
    
    // UC18: Retrieve contacts added between date ranges
    List<ContactDTO> getContactsByDateRange(String startDate, String endDate);
    
    // UC16: Retrieve all entries using JDBC
    List<ContactDTO> getAllContactsJDBC();
    
    // UC17: Update contact using JDBC
    ContactDTO updateContactJDBC(Long id, ContactDTO contactDTO);
    
    // UC20: Insert contact with transaction support
    ContactDTO insertWithTransaction(ContactDTO contactDTO);
}
