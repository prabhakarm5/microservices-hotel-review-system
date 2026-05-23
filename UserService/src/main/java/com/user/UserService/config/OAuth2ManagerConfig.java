package com.user.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;

import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

import org.springframework.util.LinkedMultiValueMap;

import org.springframework.util.MultiValueMap;

@Configuration
public class OAuth2ManagerConfig {

        @Bean
        public OAuth2AuthorizedClientManager authorizedClientManager(

                        ClientRegistrationRepository clientRegistrationRepository,

                        OAuth2AuthorizedClientRepository authorizedClientRepository) {

                RestClientClientCredentialsTokenResponseClient tokenResponseClient = new RestClientClientCredentialsTokenResponseClient();

                tokenResponseClient.addParametersConverter(request -> {

                        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

                        parameters.add(
                                        "audience",
                                        "https://hotel-review-api");

                        return parameters;
                });

                ClientCredentialsOAuth2AuthorizedClientProvider provider = new ClientCredentialsOAuth2AuthorizedClientProvider();

                provider.setAccessTokenResponseClient(
                                tokenResponseClient);

                DefaultOAuth2AuthorizedClientManager manager = new DefaultOAuth2AuthorizedClientManager(

                                clientRegistrationRepository,

                                authorizedClientRepository);

                manager.setAuthorizedClientProvider(
                                provider);

                return manager;
        }
}