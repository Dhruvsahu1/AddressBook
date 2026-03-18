package com.addressbook.io;

import com.addressbook.dto.ContactDTO;

import java.util.List;

/**
 * Interface for data sources following Open Closed Principle.
 * Implementations: DatabaseSource, CSVSource, JSONSource, JSONServerSource
 */
public interface AddressBookDataSource {
    
    List<ContactDTO> readAll();
    
    boolean write(ContactDTO contact);
    
    boolean writeAll(List<ContactDTO> contacts);
    
    boolean delete(Long id);
    
    ContactDTO update(Long id, ContactDTO contact);
}
