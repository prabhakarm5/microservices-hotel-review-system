package com.user.UserService.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;

import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.user.UserService.config.intercepter.RestTemplateInceptor;

@Configuration
public class UserConfig {

        private ClientRegistrationRepository repository;

        private OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository;

        UserConfig(
                        ClientRegistrationRepository repository,

                        OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) {

                this.repository = repository;

                this.auth2AuthorizedClientRepository = auth2AuthorizedClientRepository;
        }

        @Bean
        @LoadBalanced
        public RestTemplate restTemplate() {

                RestTemplate restTemplate = new RestTemplate();

                List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

                interceptors.add(

                                new RestTemplateInceptor(

                                                manager(
                                                                repository,
                                                                auth2AuthorizedClientRepository)));

                restTemplate.setInterceptors(interceptors);

                return restTemplate;
        }

        @Bean
        public OAuth2AuthorizedClientManager manager(

                        ClientRegistrationRepository clientRegistrationRepository,

                        OAuth2AuthorizedClientRepository authorizedClientRepository) {

                ClientCredentialsOAuth2AuthorizedClientProvider provider =

                                new ClientCredentialsOAuth2AuthorizedClientProvider();

                DefaultClientCredentialsTokenResponseClient tokenResponseClient =

                                new DefaultClientCredentialsTokenResponseClient();

                OAuth2ClientCredentialsGrantRequestEntityConverter converter =

                                new OAuth2ClientCredentialsGrantRequestEntityConverter();

                converter.addParametersConverter(request -> {

                        MultiValueMap<String, String> parameters =

                                        new LinkedMultiValueMap<>();

                        parameters.add(
                                        "audience",
                                        "https://hotel-review-api");

                        return parameters;
                });

                tokenResponseClient.setRequestEntityConverter(
                                converter);

                provider.setAccessTokenResponseClient(
                                tokenResponseClient);

                DefaultOAuth2AuthorizedClientManager manager =

                                new DefaultOAuth2AuthorizedClientManager(

                                                clientRegistrationRepository,

                                                authorizedClientRepository);

                manager.setAuthorizedClientProvider(
                                provider);

                return manager;

        }
}