package com.crew.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(
		title = "Ride Service API",
		description = "API сервиса поездок",
		version = "1.0.0"
))
public class RideApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideApplication.class, args);
	}

}
