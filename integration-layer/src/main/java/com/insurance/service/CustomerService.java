package com.insurance.service;

import com.insurance.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private final RestTemplate restTemplate;

    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Customer createCustomer(String customerName) {
        return restTemplate.postForObject(
                "http://localhost:8081/business-system/createCustomer",
                customerName,
                Customer.class);
    }
}

