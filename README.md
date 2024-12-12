# Project Documentation

## Overview
This project simulates an insurance system that handles customer agreements, mail notifications, and integration with external systems. The architecture includes controllers, services, and a notification mechanism to manage the lifecycle of agreements.

---

## Project Components

### 1. **IntegrationController**
#### Description
The entry point of the system, acting as a facade. This controller handles incoming requests and delegates tasks to various services.

#### Endpoint
- **POST** `/integration/process`
    - **Request Body**: `CustomerRequest` (JSON format)
      ```json
      {
        "customerName": "John Doe"
      }
      ```
    - **Response**: A success or failure message for processing the agreement.

#### Key Methods
- `process(CustomerRequest customerRequest)`
    - Orchestrates the full customer agreement lifecycle.

---

### 2. **BusinessSystemController**
#### Description
Simulates the backend system responsible for managing customers and agreements.

#### Endpoints
- **POST** `/business-system/createCustomer`
    - **Request Body**: `String` (customer name)
    - **Response**: `Customer` (JSON format)

- **POST** `/business-system/createAgreement`
    - **Request Body**: `String` (customer ID)
    - **Response**: `Agreement` (JSON format)

- **POST** `/business-system/updateAgreementStatus`
    - **Request Body**: `String` (agreement ID)
    - **Response**: `Agreement` with updated status.

#### Key Features
- Creates customers and agreements.
- Updates agreement statuses.

---

### 3. **MailServiceController**
#### Description
Simulates the mail service for sending agreements and returning a random status.

#### Endpoint
- **POST** `/mail-service/sendAgreement`
    - **Request Body**: `String` (agreement ID)
    - **Response**: A random `AgreementStatus` (String format).

#### Key Features
- Logs agreement IDs and randomly simulates agreement status.

---

### 4. **Services**

#### **IntegrationService**
- Orchestrates customer creation, agreement handling, and mail notifications.
- Sends notifications to observers for each significant event.

#### **CustomerService**
- Creates customers in the `BusinessSystemController`.

#### **AgreementService**
- Handles agreement creation and status updates by interacting with the `BusinessSystemController`.

#### **MailService**
- Sends agreements to the `MailServiceController` and retrieves statuses.

---

## Key Classes and Methods

### **CustomerService**
- `createCustomer(String customerName)`
    - Sends a POST request to create a customer.

### **AgreementService**
- `createAgreement(String customerId)`
    - Sends a POST request to create an agreement.
- `updateAgreementStatus(String agreementId)`
    - Updates the agreement's status.

### **IntegrationService**
- `processAgreement(String customerName)`
    - Orchestrates the full agreement process lifecycle.
- Notifies observers for customer creation, agreement creation, mail sending, and status updates.

### **MailService**
- `sendAgreement(String agreementId)`
    - Simulates sending agreements via mail.

---

## Enums

### AgreementStatus
- Represents possible statuses of an agreement.
    - `PENDING`
    - `SENT`
    - `FAILED`

---

## Sample Data
### CustomerRequest
```json
{
  "customerName": "John Doe"
}
```

### Customer
```json
{
  "id": "12345",
  "name": "John Doe"
}
```

### Agreement
```json
{
  "id": "67890",
  "customerId": "12345",
  "status": "PENDING"
}
```

---

## Logging
### Example Log Output
- **Integration Logs**
  ```plaintext
  IntegrationService: Customer created: Customer{id='12345', name='John Doe'}
  IntegrationService: Agreement created: Agreement{id='67890', customerId='12345', status='PENDING'}
  MailService: Sending agreement with ID: 67890
  MailService: Returning status: SENT
  IntegrationService: Agreement status updated: Agreement{id='67890', customerId='12345', status='SENT'}
  ```

- **Mail Service Logs**
  ```plaintext
  Mail Service: Sending agreement with ID: 67890
  Mail Service: Returning status: SENT
  ```

---

## Build and Run

### Requirements
- **Java 17**
- **Spring Boot 3.3.5**

### Steps
1. Clone the repository.
2. Navigate to the project root.
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Testing

### Unit Tests
- `IntegrationControllerTest`: Tests the `/integration/process` endpoint.
- `CustomerServiceTest`: Verifies customer creation logic.
- `AgreementServiceTest`: Validates agreement handling.
- `MailServiceControllerTest`: Tests mail service behavior.

### Mocking
- `MockMvc` for controller tests.
- `MockRestServiceServer` for service-layer tests.

---

## Future Improvements
- Implement database persistence for customers and agreements.
- Enhance error handling with custom exceptions.
- Add security with Spring Security for endpoints.
- Include monitoring and metrics using Micrometer.
- Enable asynchronous processing with Spring Async.

