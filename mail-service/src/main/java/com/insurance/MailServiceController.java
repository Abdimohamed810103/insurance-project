package com.insurance;

import com.insurance.enums.AgreementStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/mail-service")
@Slf4j
public class MailServiceController {

    @PostMapping("/sendAgreement")
    public ResponseEntity<String> sendAgreement(@RequestBody String agreementId) {
        log.info("Mail Service: Sending agreement with ID: {}", agreementId);

        // Simulate random status
        AgreementStatus[] statuses = AgreementStatus.values();
        AgreementStatus randomStatus = statuses[new Random().nextInt(statuses.length)];

        log.info("Mail Service: Returning status: {}", randomStatus);
        return ResponseEntity.ok(randomStatus.name());
    }

}

