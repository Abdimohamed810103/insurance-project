package com.insurance.service;

import com.insurance.model.Agreement;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AgreementService {

    private final RestTemplate restTemplate;

    public AgreementService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Agreement createAgreement(String customerId) {
        return restTemplate.postForObject(
                "http://localhost:8081/business-system/createAgreement",
                customerId,
                Agreement.class);
    }

    public Agreement updateAgreementStatus(String agreementId) {
        return restTemplate.postForObject(
                "http://localhost:8081/business-system/updateAgreementStatus",
                agreementId,
                Agreement.class);
    }
}

