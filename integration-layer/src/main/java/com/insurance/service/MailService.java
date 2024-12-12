package com.insurance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service responsible for handling mail operations.
 * This service communicates with the Mail Service to send agreements.
 */
@Service
public class MailService {

    private final RestTemplate restTemplate;

    /**
     * Constructor for MailService.
     *
     * @param restTemplate the RestTemplate used for making HTTP calls
     */
    public MailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sends an agreement via the Mail Service.
     *
     * @param agreementId the ID of the agreement to be sent
     * @return the response status or message from the Mail Service
     */
    public String sendAgreement(String agreementId) {
        return restTemplate.postForObject(
                "http://localhost:8083/mail-service/sendAgreement", // API endpoint
                agreementId,                                        // Request body
                String.class                                       // Response type
        );
    }
}
