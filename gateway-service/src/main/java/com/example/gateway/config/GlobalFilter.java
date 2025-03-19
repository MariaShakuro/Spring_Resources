package com.example.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilter {

    @Bean
    public org.springframework.cloud.gateway.filter.GlobalFilter logFilter() {
        return (exchange, chain) -> {
            System.out.println("Запрос: " + exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("Ответ: " + exchange.getResponse().getStatusCode());
            }));
        };
    }
}
