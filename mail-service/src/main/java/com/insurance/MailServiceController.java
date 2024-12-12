package com.insurance;

import com.insurance.enums.AgreementStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Controller for simulating the Mail Service operations.
 * This acts as a mock service for sending agreements and returning a random status.
 */
@RestController
@RequestMapping("/mail-service")
@Slf4j
public class MailServiceController {

    /**
     * Simulates the sending of an agreement via the Mail Service.
     *
     * @param agreementId the ID of the agreement to be sent
     * @return a ResponseEntity containing a random AgreementStatus
     */
    @PostMapping("/sendAgreement")
    public ResponseEntity<String> sendAgreement(@RequestBody String agreementId) {
        log.info("Mail Service: Sending agreement with ID: {}", agreementId);

        // Simulate a random status from AgreementStatus enum
        AgreementStatus[] statuses = AgreementStatus.values();
        AgreementStatus randomStatus = statuses[new Random().nextInt(statuses.length)];

        log.info("Mail Service: Returning status: {}", randomStatus);
        return ResponseEntity.ok(randomStatus.name());
    }
}
