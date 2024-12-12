package com.insurance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Configuration
@Slf4j
public class ClientApplication {

    /**
     * The main entry point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(ClientApplication.class, args);

        // Initialize the application and simulate sending requests
        ClientApplication application = new ClientApplication();
        List<CustomerRequest> names = List.of(
                new CustomerRequest("Jim"),
                new CustomerRequest("Fred"),
                new CustomerRequest("Baz"),
                new CustomerRequest("Bing")
        );

        // Send requests for each customer
        names.forEach(application::getResult);
    }

    /**
     * Sends a POST request to the integration process endpoint with the given customer data.
     *
     * @param customerRequest the customer data to be sent
     * @return the response from the server as a string
     */
    private String getResult(CustomerRequest customerRequest) {
        // Create a new RestTemplate instance for HTTP communication
        RestTemplate restTemplate = new RestTemplate();

        // Send a POST request and capture the response
        String result = restTemplate.postForObject(
                "http://localhost:8082/integration/process",
                customerRequest,
                String.class
        );

        // Log the response
        log.info(result);

        // Return the response
        return result;
    }
}
