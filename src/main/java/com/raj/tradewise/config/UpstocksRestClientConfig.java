package com.raj.tradewise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class UpstocksRestClientConfig {

    @Value("${upstocks.api.base-url}")
    private String baseUrl;

    @Value("${upstocks.api.key}")
    private String accessToken;

    @Bean
    public RestClient upstocksRestClient() {
        return RestClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
    }
}