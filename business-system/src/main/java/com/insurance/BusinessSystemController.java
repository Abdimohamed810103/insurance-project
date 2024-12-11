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

    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Agreement> agreements = new HashMap<>();

    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody String customerName) {
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId, customerName);
        customers.put(customerId, customer);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/createAgreement")
    public ResponseEntity<Agreement> createAgreement(@RequestBody String customerId) {
        if (!customers.containsKey(customerId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String agreementId = UUID.randomUUID().toString();
        Agreement agreement = new Agreement(agreementId, customerId, AgreementStatus.PENDING);
        agreements.put(agreementId, agreement);
        return ResponseEntity.ok(agreement);
    }

    @PostMapping("/updateAgreementStatus")
    public ResponseEntity<Agreement> updateAgreementStatus(@RequestBody String agreementId) {
        Agreement agreement = agreements.get(agreementId);
        if (agreement == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Agreement updatedAgreement = new Agreement(agreement.id(), agreement.customerId(), AgreementStatus.SENT);
        agreements.put(agreementId, updatedAgreement);
        return ResponseEntity.ok(updatedAgreement);
    }
}

