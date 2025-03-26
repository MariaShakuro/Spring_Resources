package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("driver-service", r -> r.path("/drivers/**")
                        .uri("lb://DRIVER-SERVICE"))
                .route("passenger-service", r -> r.path("/passenger/**")
                        .uri("lb://PASSENGER-SERVICE"))
                .route("payment-service", r -> r.path("/payments/**")
                        .uri("lb://PAYMENT-SERVICE"))
                .route("ride-service", r -> r.path("/rides/**")
                        .uri("lb://RIDE-SERVICE"))
                .build();
    }
}


