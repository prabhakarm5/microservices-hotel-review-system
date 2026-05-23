package com.gateway.ApiGateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.gateway.ApiGateway.models.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private Logger logger = LoggerFactory.getLogger(
                        AuthController.class);

        @GetMapping("/login")
        public ResponseEntity<AuthResponse> login(

                        @RegisteredOAuth2AuthorizedClient("auth0") OAuth2AuthorizedClient client,

                        @AuthenticationPrincipal OidcUser user) {

                logger.info(
                                "User Name: {}",
                                user.getFullName());

                logger.info(
                                "User Email: {}",
                                user.getEmail());

                logger.info(
                                "Access Token: {}",
                                client.getAccessToken()
                                                .getTokenValue());

                AuthResponse authResponse = new AuthResponse();

                authResponse.setUserId(
                                user.getEmail());

                // ACCESS TOKEN
                authResponse.setAccessToken(

                                client.getAccessToken()
                                                .getTokenValue());

                // REFRESH TOKEN
                if (client.getRefreshToken() != null) {

                        authResponse.setRefreshToken(

                                        client.getRefreshToken()
                                                        .getTokenValue());
                }

                // EXPIRE TIME
                authResponse.setExpiresAt(

                                client.getAccessToken()

                                                .getExpiresAt()

                                                .getEpochSecond());

                // AUTHORITIES
                List<String> authorities =

                                user.getAuthorities()

                                                .stream()

                                                .map(grantedAuthority ->

                                                grantedAuthority
                                                                .getAuthority())

                                                .collect(Collectors.toList());

                authResponse.setAuthorities(
                                authorities);

                return ResponseEntity.ok(
                                authResponse);
        }
}