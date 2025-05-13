package com.example.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.server.WebFilter;

import java.net.URI;
import java.util.Map;
/*
@Component
public class AuthenticationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Запрашиваем новый токен от Keycloak
            WebClient webClient = WebClient.builder().build();
            return webClient.post()
                    .uri("http://localhost:8080/auth/realms/spring_resources/protocol/openid-connect/token")
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                            .with("client_id", "spring-app")
                            .with("client_secret", "GFr6740iTqzHfFcB0YeQBEHyoRSKIXnJ"))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .flatMap(response -> {
                        String accessToken = (String) response.get("access_token");
                        exchange.getRequest().mutate()
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .build();
                        return chain.filter(exchange);
                    });
        }

        return chain.filter(exchange);
    }
}
*/