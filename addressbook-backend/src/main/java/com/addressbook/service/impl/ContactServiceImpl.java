package com.addressbook.service.impl;

import com.addressbook.dto.ContactDTO;
import com.addressbook.exception.ContactNotFoundException;
import com.addressbook.exception.DuplicateContactException;
import com.addressbook.model.ContactPerson;
import com.addressbook.repository.ContactRepository;
import com.addressbook.service.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of ContactService.
 * UC16-UC20: Implements JDBC operations as well.
 */
@Service
public class ContactServiceImpl implements ContactService {
    
    private final ContactRepository contactRepository;
    private final Map<String, ContactPerson> inMemoryContacts = new HashMap<>();
    
    // JDBC connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/addressbook_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";
    
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    
    // UC1: Add new contact
    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        boolean isDuplicate = contactRepository.findAll().stream()
                .anyMatch(c -> (c.getEmail() != null && c.getEmail().equals(contactDTO.getEmail())) ||
                             (c.getPhoneNumber() != null && c.getPhoneNumber().equals(contactDTO.getPhoneNumber())));
        
        if (isDuplicate) {
            throw new DuplicateContactException("Contact with same email or phone number already exists");
        }
        
        ContactPerson contact = mapToEntity(contactDTO);
        contact.setDateAdded(LocalDate.now());
        ContactPerson saved = contactRepository.save(contact);
        
        String key = saved.getFirstName() + "_" + saved.getLastName();
        inMemoryContacts.put(key.toLowerCase(), saved);
        
        return mapToDTO(saved);
    }
    
    // UC2: Edit contact using name
    @Override
    public ContactDTO editContact(String firstName, String lastName, ContactDTO contactDTO) {
        ContactPerson existing = contactRepository.findByFirstNameAndLastName(firstName, lastName);
        if (existing == null) {
            throw new ContactNotFoundException("Contact not found: " + firstName + " " + lastName);
        }
        
        existing.setAddress(contactDTO.getAddress());
        existing.setCity(contactDTO.getCity());
        existing.setState(contactDTO.getState());
        existing.setZip(contactDTO.getZip());
        existing.setPhoneNumber(contactDTO.getPhoneNumber());
        existing.setEmail(contactDTO.getEmail());
        
        ContactPerson updated = contactRepository.save(existing);
        
        String key = firstName + "_" + lastName;
        inMemoryContacts.put(key.toLowerCase(), updated);
        
        return mapToDTO(updated);
    }
    
    // UC3: Delete contact using name
    @Override
    public boolean deleteContact(String firstName, String lastName) {
        ContactPerson existing = contactRepository.findByFirstNameAndLastName(firstName, lastName);
        if (existing == null) {
            throw new ContactNotFoundException("Contact not found: " + firstName + " " + lastName);
        }
        
        contactRepository.delete(existing);
        
        String key = firstName + "_" + lastName;
        inMemoryContacts.remove(key.toLowerCase());
        
        return true;
    }
    
    // UC4: Add multiple contacts using Collections
    @Override
    public List<ContactDTO> addMultipleContacts(List<ContactDTO> contactDTOs) {
        List<ContactPerson> contacts = contactDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        
        List<ContactPerson> saved = contactRepository.saveAll(contacts);
        
        for (ContactPerson c : saved) {
            String key = c.getFirstName() + "_" + c.getLastName();
            inMemoryContacts.put(key.toLowerCase(), c);
        }
        
        return saved.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ContactDTO getContactById(Long id) {
        return contactRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));
    }
    
    // UC7: Search contacts by city or state
    @Override
    public List<ContactDTO> searchByCity(String city) {
        return contactRepository.findByCity(city).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContactDTO> searchByState(String state) {
        return contactRepository.findByState(state).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // UC8: View persons grouped by city or state
    @Override
    public Map<String, List<ContactDTO>> groupByCity() {
        return contactRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        c -> c.getCity() != null ? c.getCity() : "Unknown",
                        Collectors.mapping(this::mapToDTO, Collectors.toList())
                ));
    }
    
    @Override
    public Map<String, List<ContactDTO>> groupByState() {
        return contactRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        c -> c.getState() != null ? c.getState() : "Unknown",
                        Collectors.mapping(this::mapToDTO, Collectors.toList())
                ));
    }
    
    // UC9: Count contacts by city or state
    @Override
    public Map<String, Long> countByCity() {
        List<Object[]> results = contactRepository.countByCity();
        Map<String, Long> counts = new HashMap<>();
        for (Object[] row : results) {
            counts.put((String) row[0], (Long) row[1]);
        }
        return counts;
    }
    
    @Override
    public Map<String, Long> countByState() {
        List<Object[]> results = contactRepository.countByState();
        Map<String, Long> counts = new HashMap<>();
        for (Object[] row : results) {
            counts.put((String) row[0], (Long) row[1]);
        }
        return counts;
    }
    
    // UC10: Sort contacts alphabetically by name
    @Override
    public List<ContactDTO> sortByName() {
        return contactRepository.findAll().stream()
                .sorted(Comparator.comparing(ContactPerson::getFirstName)
                        .thenComparing(ContactPerson::getLastName))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // UC11: Sort contacts by city, state, or zip
    @Override
    public List<ContactDTO> sortByCity() {
        return contactRepository.findAll().stream()
                .sorted(Comparator.comparing(c -> c.getCity() != null ? c.getCity() : ""))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContactDTO> sortByState() {
        return contactRepository.findAll().stream()
                .sorted(Comparator.comparing(c -> c.getState() != null ? c.getState() : ""))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContactDTO> sortByZip() {
        return contactRepository.findAll().stream()
                .sorted(Comparator.comparing(c -> c.getZip() != null ? c.getZip() : ""))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // UC18: Retrieve contacts added between date ranges
    @Override
    public List<ContactDTO> getContactsByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return contactRepository.findByDateAddedBetween(start, end).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // UC16: Retrieve all entries using JDBC
    @Override
    public List<ContactDTO> getAllContactsJDBC() {
        List<ContactDTO> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                ContactDTO dto = new ContactDTO();
                dto.setId(rs.getLong("id"));
                dto.setFirstName(rs.getString("first_name"));
                dto.setLastName(rs.getString("last_name"));
                dto.setAddress(rs.getString("address"));
                dto.setCity(rs.getString("city"));
                dto.setState(rs.getString("state"));
                dto.setZip(rs.getString("zip"));
                dto.setPhoneNumber(rs.getString("phone_number"));
                dto.setEmail(rs.getString("email"));
                Date date = rs.getDate("date_added");
                if (date != null) {
                    dto.setDateAdded(date.toLocalDate());
                }
                contacts.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving contacts via JDBC", e);
        }
        
        return contacts;
    }
    
    // UC17: Update contact using JDBC and sync memory
    @Override
    public ContactDTO updateContactJDBC(Long id, ContactDTO contactDTO) {
        String query = "UPDATE contacts SET address = ?, city = ?, state = ?, zip = ?, " +
                      "phone_number = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, contactDTO.getAddress());
            pstmt.setString(2, contactDTO.getCity());
            pstmt.setString(3, contactDTO.getState());
            pstmt.setString(4, contactDTO.getZip());
            pstmt.setString(5, contactDTO.getPhoneNumber());
            pstmt.setString(6, contactDTO.getEmail());
            pstmt.setLong(7, id);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error updating contact via JDBC", e);
        }
        
        // Sync with JPA and in-memory
        return getContactById(id);
    }
    
    // UC20: Insert contact with transaction support
    @Override
    @Transactional
    public ContactDTO insertWithTransaction(ContactDTO contactDTO) {
        return addContact(contactDTO);
    }
    
    // Helper methods
    private ContactPerson mapToEntity(ContactDTO dto) {
        ContactPerson entity = new ContactPerson();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZip(dto.getZip());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setDateAdded(dto.getDateAdded());
        return entity;
    }
    
    private ContactDTO mapToDTO(ContactPerson entity) {
        ContactDTO dto = new ContactDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setZip(entity.getZip());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setDateAdded(entity.getDateAdded());
        return dto;
    }
}
