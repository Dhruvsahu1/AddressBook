# Address Book System
A complete full-stack Address Book System with Java Spring Boot backend and React frontend.

[![AddressBook CI/CD](https://github.com/Dhruvsahu1/AddressBook/actions/workflows/ci.yaml/badge.svg)](https://github.com/Dhruvsahu1/AddressBook/actions/workflows/ci.yaml)

## Features

- **Backend (Spring Boot 3+)**: RESTful API with Spring Data JPA, MySQL, JDBC support
- **Frontend (React + Vite)**: Modern UI with Bootstrap
- **Database**: MySQL
- **Testing**: JUnit + REST Assured support

## UC Implementation

- UC1: Add new contact
- UC2: Edit contact using name
- UC3: Delete contact using name
- UC4: Add multiple contacts using Collections
- UC5: Support multiple address books using Map
- UC6: Prevent duplicate contacts
- UC7: Search contacts by city or state
- UC8: View persons grouped by city or state
- UC9: Count contacts by city or state
- UC10: Sort contacts alphabetically by name
- UC11: Sort contacts by city, state, or zip
- UC12: Write contacts to file using Java File IO
- UC13: Export/Import CSV using OpenCSV
- UC14: Export/Import JSON using Gson
- UC16: Retrieve all entries using JDBC
- UC17: Update contact using JDBC and sync memory
- UC18: Retrieve contacts added between date ranges
- UC19: Count contacts by city or state using SQL group by
- UC20: Insert contact with transaction support
- UC21: Insert multiple contacts using Threads with ExecutorService
- UC22-25: REST Assured support for JSON Server

## Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

## Project Structure

```
AddressBook/
├── addressbook-backend/          # Spring Boot backend
│   ├── src/main/java/com/addressbook/
│   │   ├── controller/           # REST controllers
│   │   ├── service/              # Business logic
│   │   ├── repository/           # Data access
│   │   ├── model/                # Entity classes
│   │   ├── dto/                  # Data transfer objects
│   │   ├── exception/            # Exception handling
│   │   ├── io/                   # File IO (CSV, JSON)
│   │   └── threads/              # Multithreading
│   └── pom.xml
├── addressbook-frontend/          # React frontend
│   ├── src/
│   │   ├── services/            # API services
│   │   └── App.jsx              # Main component
│   └── package.json
├── addressbook-db.sql            # Database schema
└── README.md
```

## Setup Instructions

### 1. Database Setup

1. Start MySQL server
2. Create the database:
```sql
CREATE DATABASE addressbook_db;
```
3. Run the SQL schema:
```bash
mysql -u root -p addressbook_db < addressbook-db.sql
```

### 2. Backend Setup

1. Navigate to backend directory:
```bash
cd addressbook-backend
```

2. Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/addressbook_db
spring.datasource.username=root
spring.datasource.password=your_password
```

3. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`
Output: "Welcome to Address Book Program"

### 3. Frontend Setup

1. Navigate to frontend directory:
```bash
cd addressbook-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:5173`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/contacts | Add new contact |
| GET | /api/contacts | Get all contacts |
| GET | /api/contacts/{id} | Get contact by ID |
| PUT | /api/contacts/{id} | Update contact |
| DELETE | /api/contacts/{id} | Delete contact |
| GET | /api/contacts/sort/name | Sort by name |
| GET | /api/contacts/sort/city | Sort by city |
| GET | /api/contacts/sort/state | Sort by state |
| GET | /api/contacts/sort/zip | Sort by zip |
| GET | /api/contacts/search/city/{city} | Search by city |
| GET | /api/contacts/search/state/{state} | Search by state |
| GET | /api/contacts/group/city | Group by city |
| GET | /api/contacts/group/state | Group by state |
| GET | /api/contacts/count/city | Count by city |
| GET | /api/contacts/count/state | Count by state |
| GET | /api/contacts/jdbc | Get all via JDBC |
| PUT | /api/contacts/jdbc/{id} | Update via JDBC |

## Sample Request

```json
POST /api/contacts
{
  "firstName": "John",
  "lastName": "Doe",
  "address": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zip": "10001",
  "phoneNumber": "555-1234",
  "email": "john.doe@email.com"
}
```

## Running Tests

```bash
# Backend tests
cd addressbook-backend
mvn test
```

## Architecture

- **Clean Architecture**: Separation of concerns with Controller → Service → Repository layers
- **Open Closed Principle**: AddressBookDataSource interface with multiple implementations
- **Thread Safe**: ExecutorService and CompletableFuture for async operations

## Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Data JPA
- MySQL 8.0
- React 18
- Vite 5
- Bootstrap 5
- OpenCSV 5.9
- Gson 2.10
