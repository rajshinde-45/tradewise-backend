package com.raj.tradewise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GrowwRestClient {

    @Value("${groww.api.base-url}")
    private String baseUrl;

    @Value("${groww.api.access-token}")
    private String accessToken;

    @Bean
    public RestClient growwRestclient(){
        return RestClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer "+ accessToken)
                .build();
    }
}
