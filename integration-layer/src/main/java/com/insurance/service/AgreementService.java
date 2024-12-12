package com.insurance.service;

import com.insurance.model.Agreement;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service responsible for handling operations related to agreements.
 * This service communicates with the Business System via REST API.
 */
@Service
public class AgreementService {

    private final RestTemplate restTemplate;

    /**
     * Constructor for AgreementService.
     *
     * @param restTemplate the RestTemplate used for making HTTP calls
     */
    public AgreementService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Creates a new agreement for a given customer.
     *
     * @param customerId the ID of the customer for whom the agreement is created
     * @return the created Agreement object returned by the Business System
     */
    public Agreement createAgreement(String customerId) {
        return restTemplate.postForObject(
                "http://localhost:8081/business-system/createAgreement", // API endpoint
                customerId,                                              // Request body
                Agreement.class                                          // Response type
        );
    }

    /**
     * Updates the status of an existing agreement.
     *
     * @param agreementId the ID of the agreement to be updated
     * @return the updated Agreement object returned by the Business System
     */
    public Agreement updateAgreementStatus(String agreementId) {
        return restTemplate.postForObject(
                "http://localhost:8081/business-system/updateAgreementStatus", // API endpoint
                agreementId,                                                   // Request body
                Agreement.class                                                // Response type
        );
    }
}
