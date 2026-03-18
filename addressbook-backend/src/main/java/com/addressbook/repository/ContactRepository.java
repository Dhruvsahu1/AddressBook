package com.addressbook.repository;

import com.addressbook.model.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactPerson, Long> {
    List<ContactPerson> findByCity(String city);
    List<ContactPerson> findByState(String state);
    ContactPerson findByEmail(String email);
    ContactPerson findByPhoneNumber(String phoneNumber);
    List<ContactPerson> findByDateAddedBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT c.city, COUNT(c) FROM ContactPerson c WHERE c.city IS NOT NULL GROUP BY c.city")
    List<Object[]> countByCity();
    @Query("SELECT c.state, COUNT(c) FROM ContactPerson c WHERE c.state IS NOT NULL GROUP BY c.state")
    List<Object[]> countByState();
    ContactPerson findByFirstNameAndLastName(String firstName, String lastName);
}
