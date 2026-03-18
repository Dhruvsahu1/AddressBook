-- Address Book Database Schema
-- MySQL Database: addressbook_db

CREATE DATABASE IF NOT EXISTS addressbook_db;
USE addressbook_db;

-- Drop table if exists
DROP TABLE IF EXISTS contacts;

-- Create contacts table
CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    zip VARCHAR(20),
    phone_number VARCHAR(20),
    email VARCHAR(255),
    date_added DATE,
    
    INDEX idx_city (city),
    INDEX idx_state (state),
    INDEX idx_email (email),
    INDEX idx_date_added (date_added)
);

-- Sample data
INSERT INTO contacts (first_name, last_name, address, city, state, zip, phone_number, email, date_added) VALUES
('John', 'Doe', '123 Main St', 'New York', 'NY', '10001', '555-1234', 'john.doe@email.com', '2024-01-15'),
('Jane', 'Smith', '456 Oak Ave', 'Los Angeles', 'CA', '90001', '555-5678', 'jane.smith@email.com', '2024-01-16'),
('Bob', 'Johnson', '789 Pine Rd', 'Chicago', 'IL', '60601', '555-9012', 'bob.johnson@email.com', '2024-01-17'),
('Alice', 'Williams', '321 Elm St', 'Houston', 'TX', '77001', '555-3456', 'alice.williams@email.com', '2024-01-18'),
('Charlie', 'Brown', '654 Maple Dr', 'Phoenix', 'AZ', '85001', '555-7890', 'charlie.brown@email.com', '2024-01-19');

-- Queries for UC7, UC8, UC9
-- Search contacts by city
SELECT * FROM contacts WHERE city = 'New York';

-- Search contacts by state
SELECT * FROM contacts WHERE state = 'CA';

-- Group by city
SELECT city, COUNT(*) as count FROM contacts GROUP BY city;

-- Group by state
SELECT state, COUNT(*) as count FROM contacts GROUP BY state;

-- Count by city
SELECT city, COUNT(*) as contact_count FROM contacts WHERE city IS NOT NULL GROUP BY city;

-- Count by state
SELECT state, COUNT(*) as contact_count FROM contacts WHERE state IS NOT NULL GROUP BY state;

-- Contacts added between date range
SELECT * FROM contacts WHERE date_added BETWEEN '2024-01-01' AND '2024-12-31';

-- Sort by name
SELECT * FROM contacts ORDER BY first_name, last_name;

-- Sort by city
SELECT * FROM contacts ORDER BY city;

-- Sort by state
SELECT * FROM contacts ORDER BY state;

-- Sort by zip
SELECT * FROM contacts ORDER BY zip;
