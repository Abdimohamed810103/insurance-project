package com.insurance;

import com.insurance.enums.AgreementStatus;
import com.insurance.model.Agreement;
import com.insurance.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/business-system")
public class BusinessSystemController {

    // In-memory storage for customers and agreements
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Agreement> agreements = new HashMap<>();

    /**
     * Creates a new customer with a unique ID and name.
     *
     * @param customerName the name of the customer to be created
     * @return a ResponseEntity containing the created Customer object
     */
    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody String customerName) {
        // Generate a unique ID for the customer
        String customerId = UUID.randomUUID().toString();

        // Create and store the customer object
        Customer customer = new Customer(customerId, customerName);
        customers.put(customerId, customer);

        // Return the created customer
        return ResponseEntity.ok(customer);
    }

    /**
     * Creates a new agreement for an existing customer.
     *
     * @param customerId the ID of the customer for whom the agreement is created
     * @return a ResponseEntity containing the created Agreement object or a BAD_REQUEST status
     *         if the customer does not exist
     */
    @PostMapping("/createAgreement")
    public ResponseEntity<Agreement> createAgreement(@RequestBody String customerId) {
        // Check if the customer exists
        if (!customers.containsKey(customerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Customer not found
        }

        // Generate a unique ID for the agreement
        String agreementId = UUID.randomUUID().toString();

        // Create and store the agreement object
        Agreement agreement = new Agreement(agreementId, customerId, AgreementStatus.PENDING);
        agreements.put(agreementId, agreement);

        // Return the created agreement
        return ResponseEntity.ok(agreement);
    }

    /**
     * Updates the status of an existing agreement to SENT.
     *
     * @param agreementId the ID of the agreement to be updated
     * @return a ResponseEntity containing the updated Agreement object or a BAD_REQUEST status
     *         if the agreement does not exist
     */
    @PostMapping("/updateAgreementStatus")
    public ResponseEntity<Agreement> updateAgreementStatus(@RequestBody String agreementId) {
        // Retrieve the agreement by ID
        Agreement agreement = agreements.get(agreementId);

        // Check if the agreement exists
        if (agreement == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Agreement not found
        }

        // Update the agreement status to SENT
        Agreement updatedAgreement = new Agreement(agreement.id(), agreement.customerId(), AgreementStatus.SENT);
        agreements.put(agreementId, updatedAgreement);

        // Return the updated agreement
        return ResponseEntity.ok(updatedAgreement);
    }
}
