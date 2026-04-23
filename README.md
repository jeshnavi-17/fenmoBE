# Expense Tracker Backend

## Overview

This is a Spring Boot-based backend application for an Expense Tracker system. It provides REST APIs to create, retrieve, filter, and summarize expenses. The application is designed with production-ready practices including validation, idempotency, pagination, and global exception handling.

---

## Tech Stack

- Java 17  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- Maven  

---

## Features

- Create expense with validation  
- Idempotent API for safe retries  
- Fetch expenses with pagination and sorting  
- Filter expenses by category (case-insensitive)  
- Category-wise total aggregation  
- Global exception handling  
- Input validation using annotations  
- Transaction management  

---

## API Endpoints

### Create Expense

POST /expenses

Headers:
- Content-Type: application/json  
- Idempotency-Key: unique-key  

Request Body:
```json
{
  "amount": 100.50,
  "category": "food",
  "description": "Lunch",
  "date": "2026-04-20"
}
```

---

### Get Expenses

GET /expenses?page=0&size=10&sort=date,desc

Optional Query Params:
- category (case-insensitive filter)

Example:
```
/expenses?page=0&size=10&sort=date,asc&category=food
```

---

### Get Category Summary

GET /expenses/summary

Response:
```json
[
  {
    "category": "food",
    "total": 500.00
  },
  {
    "category": "travel",
    "total": 1200.00
  }
]
```

---

## Database Configuration

Update `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Running the Application

```bash
mvn spring-boot:run
```

Application runs on:
```
http://localhost:8080
```

---

## Validation Rules

- amount must be positive  
- category cannot be blank  
- date cannot be null  
- amount supports up to 8 digits and 2 decimal places  

---

## Idempotency

To prevent duplicate expense creation, each request must include an `Idempotency-Key` header.

If the same key is reused, the request will not create duplicate entries.

---

## Sorting and Pagination

- Default sorting: `date DESC`  
- Supports custom sorting via query params  
- Pagination supported via `page` and `size`  

---

## Exception Handling

The application uses a global exception handler to manage:

- Validation errors  
- Duplicate idempotency key  
- Resource not found  
- Generic server errors  

All errors return structured JSON responses.

---

## Project Structure

```
controller/        -> REST controllers  
service/           -> business logic  
repository/        -> database layer  
entity/            -> JPA entities  
dto/               -> request/response objects  
exception/         -> custom exceptions and handlers  
config/            -> configuration classes  
```

---

## Notes

- Categories are normalized to lowercase before storing to maintain consistency  
- Filtering is case-insensitive  
- Aggregation is handled at database level using JPQL  

---
