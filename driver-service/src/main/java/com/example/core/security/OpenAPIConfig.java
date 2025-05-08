package com.example.core.security;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("keycloak"))
                .components(new Components()
                        .addSecuritySchemes("keycloak",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .scheme("bearer")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl("http://localhost:8080/auth/realms/spring_resources/protocol/openid-connect/auth")
                                                        .tokenUrl("http://localhost:8080/auth/realms/spring_resources/protocol/openid-connect/token")
                                                )
                                                .password(new OAuthFlow()
                                                        .tokenUrl("http://localhost:8080/auth/realms/spring_resources/protocol/openid-connect/token")
                                                )
                                        )
                        )
                );
    }
}
*/