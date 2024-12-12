package com.insurance.service;

import com.insurance.model.Agreement;
import com.insurance.model.Customer;
import com.insurance.notification.Subject;
import com.insurance.notification.SubjectImpl;
import org.springframework.stereotype.Service;

/**
 * Service responsible for orchestrating the integration process.
 * This service handles customer creation, agreement management,
 * mail notification, and status updates while notifying observers
 * of important events.
 */
@Service
public class IntegrationService {

    private final CustomerService customerService;
    private final AgreementService agreementService;
    private final MailService mailService;
    private final Subject subject;

    private static final String SENT_STATUS = "SENT";

    /**
     * Constructor for IntegrationService.
     *
     * @param customerService   the service for managing customers
     * @param agreementService  the service for managing agreements
     * @param mailService       the service for sending agreements via mail
     * @param subject           the notification subject for observer updates
     */
    public IntegrationService(CustomerService customerService,
                              AgreementService agreementService,
                              MailService mailService,
                              SubjectImpl subject) {
        this.customerService = customerService;
        this.agreementService = agreementService;
        this.mailService = mailService;
        this.subject = subject;
    }

    /**
     * Orchestrates the entire agreement process for a given customer.
     *
     * @param customerName the name of the customer
     * @return a success or failure message based on the process outcome
     */
    public String processAgreement(String customerName) {
        try {
            // Step 1: Create a customer
            Customer customer = createCustomer(customerName);

            // Step 2: Create an agreement for the customer
            Agreement agreement = createAgreement(customer.id());

            // Step 3: Send the agreement via mail
            String mailStatus = sendAgreement(agreement.id());

            // Step 4: Handle mail service failure
            if (!SENT_STATUS.equals(mailStatus)) {
                handleFailedMailService(agreement);
                return generateFailureResponse(agreement.customerId());
            }

            // Step 5: Update the agreement status upon successful mail delivery
            Agreement updatedAgreement = updateAgreementStatus(agreement.id());

            // Step 6: Return a success response
            return generateSuccessResponse(agreement.customerId(), updatedAgreement.status().name());

        } catch (Exception e) {
            notify("Error in process: " + e.getMessage());
            throw new RuntimeException("Error in process: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a new customer and notifies observers.
     */
    private Customer createCustomer(String customerName) {
        Customer customer = customerService.createCustomer(customerName);
        notify("Customer created: " + customer);
        return customer;
    }

    /**
     * Creates a new agreement for the specified customer and notifies observers.
     */
    private Agreement createAgreement(String customerId) {
        Agreement agreement = agreementService.createAgreement(customerId);
        notify("Agreement created: " + agreement);
        return agreement;
    }

    /**
     * Sends the agreement via the mail service and notifies observers of the result.
     */
    private String sendAgreement(String agreementId) {
        String mailStatus = mailService.sendAgreement(agreementId);
        notify("Mail Service returned status: " + mailStatus);
        return mailStatus;
    }

    /**
     * Handles mail service failure scenarios and notifies observers.
     */
    private void handleFailedMailService(Agreement agreement) {
        notify("Error: Mail Service failed to send the agreement for ID: " + agreement.id());
    }

    /**
     * Updates the agreement status to SENT and notifies observers.
     */
    private Agreement updateAgreementStatus(String agreementId) {
        try {
            Agreement updatedAgreement = agreementService.updateAgreementStatus(agreementId);
            notify("Agreement status updated: " + updatedAgreement);
            return updatedAgreement;
        } catch (Exception e) {
            notify("Fagsystemet status update failed for agreement: " + agreementId);
            throw new RuntimeException("Fagsystemet status update failed.", e);
        }
    }

    /**
     * Generates a failure response message for the agreement process.
     */
    private String generateFailureResponse(String customerId) {
        return "Agreement process Failed. Agreement number: " + customerId +
                ", Agreement Status: Failed";
    }

    /**
     * Generates a success response message for the agreement process.
     */
    private String generateSuccessResponse(String customerId, String status) {
        return "Agreement process completed. Agreement number: " + customerId +
                ", Agreement Status: " + status;
    }

    /**
     * Notifies observers of an event or message.
     */
    private void notify(String message) {
        subject.notifyObservers(message);
    }
}
