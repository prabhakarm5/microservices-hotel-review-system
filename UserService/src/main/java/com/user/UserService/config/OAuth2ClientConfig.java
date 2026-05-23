package com.user.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;

import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import org.springframework.util.LinkedMultiValueMap;

import org.springframework.util.MultiValueMap;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    public RestClientClientCredentialsTokenResponseClient tokenResponseClient() {

        RestClientClientCredentialsTokenResponseClient client = new RestClientClientCredentialsTokenResponseClient();

        client.addParametersConverter(request -> {

            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

            parameters.add(
                    "audience",
                    "https://hotel-review-api");

            return parameters;
        });

        return client;
    }
}