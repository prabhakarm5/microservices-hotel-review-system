package com.gateway.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(
                        ServerHttpSecurity http) {

                return http

                                // DISABLE CSRF
                                .csrf(csrf -> csrf.disable())

                                // AUTHORIZATION
                                .authorizeExchange(exchange -> exchange

                                                // PUBLIC ENDPOINTS
                                                .pathMatchers(

                                                                "/",

                                                                "/auth/**",

                                                                "/oauth2/**",

                                                                "/login/**")

                                                .permitAll()

                                                // SECURED ENDPOINTS
                                                .anyExchange()

                                                .authenticated())

                                // OAUTH2 LOGIN
                                .oauth2Login(
                                                Customizer.withDefaults())

                                // JWT RESOURCE SERVER
                                .oauth2ResourceServer(oauth2 -> oauth2

                                                .jwt(Customizer.withDefaults()))

                                .build();
        }
}