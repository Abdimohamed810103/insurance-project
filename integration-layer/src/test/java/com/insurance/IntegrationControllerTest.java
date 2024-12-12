package com.insurance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.notification.FagsystemStatusObserver;
import com.insurance.notification.SubjectImpl;
import com.insurance.service.IntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = IntegrationLayerApplication.class)
public class IntegrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntegrationService integrationService;

    @MockBean
    private SubjectImpl subject;

    @MockBean
    private FagsystemStatusObserver fagsystemStatusObserver;

    @BeforeEach
    void setUp() {
        // Mock subject behavior if necessary
        Mockito.doNothing().when(subject).registerObserver(fagsystemStatusObserver);
    }

    @Test
    void shouldReturnSuccessResponseWhenProcessAgreementSucceeds() throws Exception {
        // Arrange
        CustomerRequest customerRequest = new CustomerRequest("Abdi Mohamed");
        String successResponse = "Agreement process completed. Agreement number: 8600feea-0eb8-492f-8564-fa128597ed49, Agreement Status: SENT";
        when(integrationService.processAgreement(customerRequest.customerName())).thenReturn(successResponse);

        // Act & Assert
        mockMvc.perform(post("/integration/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(customerRequest))))
                .andExpect(status().isOk())
                .andExpect(content().string(successResponse));
    }

    @Test
    void shouldReturnErrorResponseWhenProcessAgreementFails() throws Exception {
        // Arrange
        CustomerRequest customerRequest = new CustomerRequest("Abdi Mohamed");
        String errorMessage = "Error in processing agreement.";
        when(integrationService.processAgreement(customerRequest.customerName())).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        mockMvc.perform(post("/integration/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(customerRequest))))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(errorMessage));
    }

    private String objectToJson(Object object){
        try {
            return  new ObjectMapper().writeValueAsString(object);
        }catch (JsonProcessingException e){
            fail("Failed to convert object to json");
            return null;
        }
    }

}
