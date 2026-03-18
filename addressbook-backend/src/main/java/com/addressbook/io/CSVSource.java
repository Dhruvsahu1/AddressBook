package com.addressbook.io;

import com.addressbook.dto.ContactDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV Data Source implementation using OpenCSV.
 * UC13: Export / Import CSV using OpenCSV
 */
@Component
public class CSVSource implements AddressBookDataSource {
    
    private static final String CSV_FILE_PATH = "contacts.csv";
    
    @Override
    public List<ContactDTO> readAll() {
        List<ContactDTO> contacts = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            List<String[]> rows = reader.readAll();
            
            // Skip header row
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                ContactDTO contact = new ContactDTO();
                contact.setId(Long.parseLong(row[0]));
                contact.setFirstName(row[1]);
                contact.setLastName(row[2]);
                contact.setAddress(row[3]);
                contact.setCity(row[4]);
                contact.setState(row[5]);
                contact.setZip(row[6]);
                contact.setPhoneNumber(row[7]);
                contact.setEmail(row[8]);
                contacts.add(contact);
            }
        } catch (IOException | CsvException e) {
            // Return empty list if file doesn't exist
        }
        
        return contacts;
    }
    
    @Override
    public boolean write(ContactDTO contact) {
        List<ContactDTO> contacts = readAll();
        contacts.add(contact);
        return writeAll(contacts);
    }
    
    @Override
    public boolean writeAll(List<ContactDTO> contacts) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write header
            String[] header = {"id", "firstName", "lastName", "address", "city", "state", "zip", "phoneNumber", "email"};
            writer.writeNext(header);
            
            // Write contacts
            for (ContactDTO contact : contacts) {
                String[] row = {
                    String.valueOf(contact.getId()),
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getAddress(),
                    contact.getCity(),
                    contact.getState(),
                    contact.getZip(),
                    contact.getPhoneNumber(),
                    contact.getEmail()
                };
                writer.writeNext(row);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public boolean delete(Long id) {
        List<ContactDTO> contacts = readAll();
        contacts.removeIf(c -> c.getId().equals(id));
        return writeAll(contacts);
    }
    
    @Override
    public ContactDTO update(Long id, ContactDTO contact) {
        List<ContactDTO> contacts = readAll();
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                contact.setId(id);
                contacts.set(i, contact);
                writeAll(contacts);
                return contact;
            }
        }
        return null;
    }
}
