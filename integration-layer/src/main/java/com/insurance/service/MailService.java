package com.insurance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailService {

    private final RestTemplate restTemplate;

    public MailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendAgreement(String agreementId) {
        return restTemplate.postForObject(
                "http://localhost:8083/mail-service/sendAgreement",
                agreementId,
                String.class);
    }
}

