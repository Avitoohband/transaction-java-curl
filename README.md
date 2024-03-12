# Transaction Java cURL

## About

<b>This repository contains a Spring Boot application designed to handle transaction operations. It supports various
functionalities including creating, updating, deleting, and fetching transactions. Additionally, it provides the ability
to filter transactions by type, amount, and date range, and to calculate a summary of transactions.</b>

### Prerequisites

- Java JDK 11 or newer
- Gradle
- An IDE of your choice (IntelliJ IDEA, Eclipse, VSCode, etc.)
- Docker (optional, for containerization)

### Installing

Clone the repository:

```git clone https://github.com/Avitoohband/transaction-java-curl.git```

Navigate to the project directory:

```cd transaction-java-curl```

Build the project:

`````./gradlew build`````

### Getting Started

in application.yaml, make sure to have this configuration</br>

```
# H2 Base Configuration.
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL
spring.datasource.driverClassName=org.h2.Driver
# Making it available at http://localhost:8080/h2-console
spring.h2.console.enabled=true
spring.datasource.username=sa
spring.datasource.password=password

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

in application-test.yaml, make sure to have this configuration</br>

```
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```

make sure to have these files in your both resources and test resources folders:

<h3>schema.sql</h3>

```agsl
CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255),
    transaction_date DATE NOT NULL
);
```

<h3>data.sql</h3>

```
INSERT INTO transaction (type, amount, description, transaction_date) VALUES
('credit', 15000, 'משכורת', '2024-01-01'),
('credit', 300, 'זכייה בפיס', '2024-01-06'),
('debit', -130.56, 'חיוב אשראי', '2024-01-01'),
('debit', -1000, 'הורדת צק', '2024-01-03'),
('debit', -235.60, 'חיוב אשראי', '2024-01-01'),
('debit', -100, 'עמלה', '2024-01-02'),
('debit', -1000, 'משיכת מזומן', '2024-01-03'),
('credit', 15432, 'משכורת', '2024-02-01'),
('credit', 5000, 'העברה מאמא', '2024-02-10'),
('debit', -130.56, 'חיוב אשראי', '2024-02-01'),
('debit', -2456, 'הורדת צק', '2024-02-03'),
('debit', -235.60, 'חיוב אשראי', '2024-02-01'),
('debit', -93.43, 'עמלה', '2024-02-02'),
('debit', -1234, 'משיכת מזומן', '2024-02-03');
```

### Run the application:

`````./gradlew bootRun`````

The application should now be running on http://localhost:8080.

## API

Usage
This application provides a RESTFul API for transaction management. Below are examples of how to interact with the API
using cURL commands:

* Create a new transaction: </br>```curl -X POST http://localhost:8080/api/transactions
  -H 'Content-Type: application/json' \
  -d '{
  "type": "credit",
  "amount": 100.0,
  "description": "Salary",
  "transactionDate": "2023-03-15"
  }'```</br>
* Update a transaction: </br>```curl -X PUT http://localhost:8080/api/transactions/{id} \
  -H 'Content-Type: application/json' \
  -d '{
  "type": "debit",
  "amount": 50.0,
  "description": "Groceries",
  "transactionDate": "2023-03-16"
  }'```</br>
  Replace {id} with the ID of the transaction you wish to update.</br>

* Delete a transaction: </br>```curl -X DELETE http://localhost:8080/api/transactions/{id}```
  </br>Replace {id} with the ID of the transaction you wish to delete.

## References

* Visit http://localhost:8080/swagger-ui/index.html for more API information.</br>
* Try using this postman
collection [TransactionCurlAPi.postman_collection.json](TransactionCurlAPi.postman_collection.json)