# Product Management API (Quarkus)

This is a **Quarkus** application providing REST APIs to manage products.  
It supports CRUD operations, validation, and basic error handling.

---

## 🛠 Features

- Create, Read, Update, Delete (CRUD) products
- Input validation
- Global exception handling
- Unit and integration tests included
- Postman collection available for testing

---

## 📦 Prerequisites

- Java 25
- Maven
- Git
- [Postman]

---

## ⚡ How to Run

1. Clone the repository:

```bash
git clone https://github.com/Muhad16/my-product-api.git
cd my-product-api

mvn clean install

mvn quarkus:dev

http://localhost:8080


Postman Collection

Import postman_collection.json in Postman to test all endpoints.

Example endpoints included:

Get all products

Create a product

Update a product

Delete a product

Check Stock

Make sure Java 25 is installed.
