package com.user.UserService.config.intercepter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;

import feign.RequestTemplate;

@Component
public class FeignClientIntercepter
        implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager manager;

    @Override
    public void apply(
            RequestTemplate requestTemplate) {

        OAuth2AuthorizedClient client =

                manager.authorize(

                        OAuth2AuthorizeRequest

                                .withClientRegistrationId(
                                        "my-internal-client")

                                .principal("internal-user")

                                .build());

        if (client != null) {

            String token = client

                    .getAccessToken()

                    .getTokenValue();

            requestTemplate.header(
                    "Authorization",
                    "Bearer " + token);
        }
    }
}