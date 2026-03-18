package com.addressbook.controller;

import com.addressbook.dto.APIResponse;
import com.addressbook.dto.ContactDTO;
import com.addressbook.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Contact operations.
 * Handles all HTTP requests for contact management.
 */
@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {
    
    private final ContactService contactService;
    
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    
    // UC1: Add new contact
    @PostMapping
    public ResponseEntity<APIResponse<ContactDTO>> addContact(@Valid @RequestBody ContactDTO contactDTO) {
        ContactDTO saved = contactService.addContact(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success("Contact added successfully", saved));
    }
    
    // Get all contacts
    @GetMapping
    public ResponseEntity<APIResponse<List<ContactDTO>>> getAllContacts() {
        List<ContactDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // Get contact by id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ContactDTO>> getContactById(@PathVariable Long id) {
        ContactDTO contact = contactService.getContactById(id);
        return ResponseEntity.ok(APIResponse.success(contact));
    }
    
    // UC2: Edit contact using name
    @PutMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<APIResponse<ContactDTO>> editContact(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody ContactDTO contactDTO) {
        ContactDTO updated = contactService.editContact(firstName, lastName, contactDTO);
        return ResponseEntity.ok(APIResponse.success("Contact updated successfully", updated));
    }
    
    // UC3: Delete contact using name
    @DeleteMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<APIResponse<Void>> deleteContact(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        boolean deleted = contactService.deleteContact(firstName, lastName);
        if (deleted) {
            return ResponseEntity.ok(APIResponse.success("Contact deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse.error("Contact not found"));
    }
    
    // UC4: Add multiple contacts
    @PostMapping("/bulk")
    public ResponseEntity<APIResponse<List<ContactDTO>>> addMultipleContacts(@RequestBody List<ContactDTO> contacts) {
        List<ContactDTO> saved = contactService.addMultipleContacts(contacts);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success("Contacts added successfully", saved));
    }
    
    // UC7: Search contacts by city
    @GetMapping("/search/city/{city}")
    public ResponseEntity<APIResponse<List<ContactDTO>>> searchByCity(@PathVariable String city) {
        List<ContactDTO> contacts = contactService.searchByCity(city);
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC7: Search contacts by state
    @GetMapping("/search/state/{state}")
    public ResponseEntity<APIResponse<List<ContactDTO>>> searchByState(@PathVariable String state) {
        List<ContactDTO> contacts = contactService.searchByState(state);
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC8: Group contacts by city
    @GetMapping("/group/city")
    public ResponseEntity<APIResponse<Map<String, List<ContactDTO>>>> groupByCity() {
        Map<String, List<ContactDTO>> grouped = contactService.groupByCity();
        return ResponseEntity.ok(APIResponse.success(grouped));
    }
    
    // UC8: Group contacts by state
    @GetMapping("/group/state")
    public ResponseEntity<APIResponse<Map<String, List<ContactDTO>>>> groupByState() {
        Map<String, List<ContactDTO>> grouped = contactService.groupByState();
        return ResponseEntity.ok(APIResponse.success(grouped));
    }
    
    // UC9: Count by city
    @GetMapping("/count/city")
    public ResponseEntity<APIResponse<Map<String, Long>>> countByCity() {
        Map<String, Long> counts = contactService.countByCity();
        return ResponseEntity.ok(APIResponse.success(counts));
    }
    
    // UC9: Count by state
    @GetMapping("/count/state")
    public ResponseEntity<APIResponse<Map<String, Long>>> countByState() {
        Map<String, Long> counts = contactService.countByState();
        return ResponseEntity.ok(APIResponse.success(counts));
    }
    
    // UC10: Sort by name
    @GetMapping("/sort/name")
    public ResponseEntity<APIResponse<List<ContactDTO>>> sortByName() {
        List<ContactDTO> contacts = contactService.sortByName();
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC11: Sort by city
    @GetMapping("/sort/city")
    public ResponseEntity<APIResponse<List<ContactDTO>>> sortByCity() {
        List<ContactDTO> contacts = contactService.sortByCity();
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC11: Sort by state
    @GetMapping("/sort/state")
    public ResponseEntity<APIResponse<List<ContactDTO>>> sortByState() {
        List<ContactDTO> contacts = contactService.sortByState();
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC11: Sort by zip
    @GetMapping("/sort/zip")
    public ResponseEntity<APIResponse<List<ContactDTO>>> sortByZip() {
        List<ContactDTO> contacts = contactService.sortByZip();
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC18: Get contacts by date range
    @GetMapping("/date-range")
    public ResponseEntity<APIResponse<List<ContactDTO>>> getContactsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<ContactDTO> contacts = contactService.getContactsByDateRange(startDate, endDate);
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC16: Get all contacts via JDBC
    @GetMapping("/jdbc")
    public ResponseEntity<APIResponse<List<ContactDTO>>> getAllContactsJDBC() {
        List<ContactDTO> contacts = contactService.getAllContactsJDBC();
        return ResponseEntity.ok(APIResponse.success(contacts));
    }
    
    // UC17: Update contact via JDBC
    @PutMapping("/jdbc/{id}")
    public ResponseEntity<APIResponse<ContactDTO>> updateContactJDBC(
            @PathVariable Long id,
            @RequestBody ContactDTO contactDTO) {
        ContactDTO updated = contactService.updateContactJDBC(id, contactDTO);
        return ResponseEntity.ok(APIResponse.success("Contact updated via JDBC", updated));
    }
    
    // UC20: Insert with transaction
    @PostMapping("/transaction")
    public ResponseEntity<APIResponse<ContactDTO>> insertWithTransaction(@RequestBody ContactDTO contactDTO) {
        ContactDTO saved = contactService.insertWithTransaction(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success("Contact inserted with transaction", saved));
    }
    
    // Update contact by id (standard REST)
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ContactDTO>> updateContact(
            @PathVariable Long id,
            @RequestBody ContactDTO contactDTO) {
        ContactDTO existing = contactService.getContactById(id);
        ContactDTO updated = contactService.editContact(existing.getFirstName(), existing.getLastName(), contactDTO);
        return ResponseEntity.ok(APIResponse.success("Contact updated successfully", updated));
    }
    
    // Delete contact by id
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteContactById(@PathVariable Long id) {
        ContactDTO existing = contactService.getContactById(id);
        boolean deleted = contactService.deleteContact(existing.getFirstName(), existing.getLastName());
        if (deleted) {
            return ResponseEntity.ok(APIResponse.success("Contact deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse.error("Contact not found"));
    }
}
