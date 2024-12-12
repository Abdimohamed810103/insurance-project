package com.insurance.service;

import com.insurance.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service responsible for handling operations related to customers.
 * This service communicates with the Business System via REST API.
 */
@Service
public class CustomerService {

    private final RestTemplate restTemplate;

    /**
     * Constructor for CustomerService.
     *
     * @param restTemplate the RestTemplate used for making HTTP calls
     */
    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Creates a new customer in the Business System.
     *
     * @param customerName the name of the customer to be created
     * @return the created Customer object returned by the Business System
     */
    public Customer createCustomer(String customerName) {
        return restTemplate.postForObject(
                "http://localhost:8081/business-system/createCustomer", // API endpoint
                customerName,                                           // Request body
                Customer.class                                          // Response type
        );
    }
}
