package com.addressbook.io;

import com.addressbook.dto.ContactDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON Data Source implementation using Gson.
 * UC14: Export / Import JSON using Gson
 */
@Component
public class JSONSource implements AddressBookDataSource {
    
    private static final String JSON_FILE_PATH = "contacts.json";
    private final Gson gson = new Gson();
    
    @Override
    public List<ContactDTO> readAll() {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<ContactDTO>>(){}.getType();
            List<ContactDTO> contacts = gson.fromJson(reader, listType);
            return contacts != null ? contacts : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean write(ContactDTO contact) {
        List<ContactDTO> contacts = readAll();
        contacts.add(contact);
        return writeAll(contacts);
    }
    
    @Override
    public boolean writeAll(List<ContactDTO> contacts) {
        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(contacts, writer);
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
