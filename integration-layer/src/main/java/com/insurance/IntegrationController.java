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

/**
 * IntegrationController acts as the entry point of the system, serving as a facade
 * to handle requests and delegate processing to underlying services.
 */
@RestController
@RequestMapping("/integration")
public class IntegrationController {

    private final IntegrationService integrationService;
    private final SubjectImpl subject;
    private final FagsystemStatusObserver fagsystemStatusObserver;

    /**
     * Constructor to initialize IntegrationController with dependencies.
     *
     * @param integrationService the service handling integration logic
     * @param subject the subject used for observer notifications
     * @param fagsystemStatusObserver the observer monitoring Fagsystemet status
     */
    public IntegrationController(IntegrationService integrationService,
                                 SubjectImpl subject,
                                 FagsystemStatusObserver fagsystemStatusObserver) {
        this.integrationService = integrationService;
        this.subject = subject;
        this.fagsystemStatusObserver = fagsystemStatusObserver;

        // Register the observer to monitor Fagsystemet status
        this.subject.registerObserver(fagsystemStatusObserver);
    }

    /**
     * Handles the processing of customer agreements.
     *
     * @param customerRequest the request containing customer information
     * @return a ResponseEntity with the processing result or an error message
     */
    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody CustomerRequest customerRequest) {
        try {
            // Delegate agreement processing to the service layer
            String result = integrationService.processAgreement(customerRequest.customerName());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Return an error response in case of failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
