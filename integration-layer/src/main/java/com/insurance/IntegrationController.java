package com.insurance;

import com.insurance.notification.FagsystemStatusObserver;
import com.insurance.notification.SubjectImpl;
import com.insurance.service.IntegrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/integration")
public class IntegrationController {

    private final IntegrationService integrationService;
    private final SubjectImpl subject;
    private final FagsystemStatusObserver fagsystemStatusObserver;

    public IntegrationController(IntegrationService integrationService,
                                 SubjectImpl subject,
                                 FagsystemStatusObserver fagsystemStatusObserver) {
        this.integrationService = integrationService;
        this.subject = subject;
        this.fagsystemStatusObserver = fagsystemStatusObserver;

        // Register the observer for Fagsystemet failure
        this.subject.registerObserver(fagsystemStatusObserver);
    }

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody String customerName) {
        try {
            String result = integrationService.processAgreement("abdi Mohamed");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

