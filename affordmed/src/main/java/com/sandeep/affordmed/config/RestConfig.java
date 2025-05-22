package com.sandeep.affordmed.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class RestConfig {
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiZXhwIjoxNzQ3ODk5NjMzLCJpYXQiOjE3NDc4OTkzMzMsImlzcyI6IkFmZm9yZG1lZCIsImp0aSI6IjcxMjkzZjg0LTg2NGUtNDNlNy1iMTk1LWE0NzU1NDE0NzA5YyIsInN1YiI6IjIyMDAwMzIyMzVjc2VoQGdtYWlsLmNvbSJ9LCJlbWFpbCI6IjIyMDAwMzIyMzVjc2VoQGdtYWlsLmNvbSIsIm5hbWUiOiJtYXJuaSB0YXJ1biBzYW5kZXAiLCJyb2xsTm8iOiIyMjAwMDMyMjM1IiwiYWNjZXNzQ29kZSI6ImJlVEpqSiIsImNsaWVudElEIjoiNzEyOTNmODQtODY0ZS00M2U3LWIxOTUtYTQ3NTU0MTQ3MDljIiwiY2xpZW50U2VjcmV0IjoidEd2dmVhS1BQSlV2QWZrRSJ9.k0a-R6gio-n-I4m_DIN4PCVpJyqyjNNvgmYiEhXD37o"; // Replace with your actual token


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + TOKEN);
                    return execution.execute(request, body);
                })
                .build();
    }
}

