package com.insurance.service;

import com.insurance.model.Agreement;
import com.insurance.model.Customer;
import com.insurance.notification.Subject;
import com.insurance.notification.SubjectImpl;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

    private final CustomerService customerService;
    private final AgreementService agreementService;
    private final MailService mailService;
    private final Subject subject;

    private static final String SENT_STATUS = "SENT";

    public IntegrationService(CustomerService customerService,
                              AgreementService agreementService,
                              MailService mailService,
                              SubjectImpl subject) {
        this.customerService = customerService;
        this.agreementService = agreementService;
        this.mailService = mailService;
        this.subject = subject;
    }

    public String processAgreement(String customerName) {
        try {
            Customer customer = createCustomer(customerName);
            Agreement agreement = createAgreement(customer.id());
            String mailStatus = sendAgreement(agreement.id());

            if (!SENT_STATUS.equals(mailStatus)) {
                handleFailedMailService(agreement);
                return generateFailureResponse(agreement.customerId());
            }

            Agreement updatedAgreement = updateAgreementStatus(agreement.id());
            return generateSuccessResponse(agreement.customerId(), updatedAgreement.status().name());

        } catch (Exception e) {
            notify("Error in process: " + e.getMessage());
            throw new RuntimeException("Error in process: " + e.getMessage(), e);
        }
    }

    private Customer createCustomer(String customerName) {
        Customer customer = customerService.createCustomer(customerName);
        notify("Customer created: " + customer);
        return customer;
    }

    private Agreement createAgreement(String customerId) {
        Agreement agreement = agreementService.createAgreement(customerId);
        notify("Agreement created: " + agreement);
        return agreement;
    }

    private String sendAgreement(String agreementId) {
        String mailStatus = mailService.sendAgreement(agreementId);
        notify("Mail Service returned status: " + mailStatus);
        return mailStatus;
    }

    private void handleFailedMailService(Agreement agreement) {
        notify("Error: Mail Service failed to send the agreement for ID: " + agreement.id());
    }

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

    private String generateFailureResponse(String customerId) {
        return "Agreement process Failed. Agreement number: " + customerId +
                ", Agreement Status: Failed";
    }

    private String generateSuccessResponse(String customerId, String status) {
        return "Agreement process completed. Agreement number: " + customerId +
                ", Agreement Status: " + status;
    }

    private void notify(String message) {
        subject.notifyObservers(message);
    }
}
