package com.insurance;

import com.insurance.enums.AgreementStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/mail-service")
public class MailServiceController {

    @PostMapping("/sendAgreement")
    public ResponseEntity<String> sendAgreement(@RequestBody String agreementId) {
        System.out.println("Mail Service: Sending agreement with ID: " + agreementId);

        // Simulate random status
        AgreementStatus[] statuses = AgreementStatus.values();
        AgreementStatus randomStatus = statuses[new Random().nextInt(statuses.length)];

        System.out.println("Mail Service: Returning status: " + randomStatus);
        return ResponseEntity.ok(randomStatus.name());
    }

}

