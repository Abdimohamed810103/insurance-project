package com.insurance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@Configuration
@Slf4j
public class ClientApplication {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        ClientApplication application = new ClientApplication();
        List<String> names = Arrays.asList("Jim", "Fred", "Baz", "Bing");
        Collections.shuffle(names );
        names.forEach(application::getResult);
    }


    private String getResult(String name) {
        var result = restTemplate().postForObject(
                "http://localhost:8082/integration/process",
                name,
                String.class);
        log.info(result);
        return result;
    }


}
