package com.user.UserService.config.intercepter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;

import org.springframework.http.client.ClientHttpRequestExecution;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import org.springframework.http.client.ClientHttpResponse;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class RestTemplateInceptor
                implements ClientHttpRequestInterceptor {

        private OAuth2AuthorizedClientManager manager;

        private Logger logger = LoggerFactory.getLogger(RestTemplateInceptor.class);

        public RestTemplateInceptor(
                        OAuth2AuthorizedClientManager manager) {

                this.manager = manager;
        }

        @Override
        public ClientHttpResponse intercept(
                        HttpRequest request,
                        byte[] body,
                        ClientHttpRequestExecution execution)
                        throws IOException {

                OAuth2AuthorizedClient client = manager.authorize(

                                OAuth2AuthorizeRequest

                                                .withClientRegistrationId(
                                                                "my-internal-client")

                                                .principal("internal-user")

                                                .build());

                if (client != null) {

                        String token = client
                                        .getAccessToken()
                                        .getTokenValue();

                        request.getHeaders()
                                        .add(
                                                        "Authorization",
                                                        "Bearer " + token);

                        logger.info("Rest Template Interceptor {}", token);
                }

                return execution.execute(request, body);
        }
}