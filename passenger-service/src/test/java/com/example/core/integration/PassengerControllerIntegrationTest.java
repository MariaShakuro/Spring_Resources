package com.example.core.integration;

import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PassengerControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private PassengerRepository passengerRepository;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        passengerRepository.save(new Passenger(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123"));
    }

    @Test
    public void testRegisterPassenger() {
        PassengerDto passengerDto = new PassengerDto(null, "Bob", "bob@example.com", "password456", "0987654321", "PROMO456");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passengerDto)
                .when()
                .post("/api/passenger/register-and-send-event")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Bob"))
                .body("email", equalTo("bob@example.com"))
                .body("promocode", equalTo("PROMO456"));
    }


    @Test
    public void testDeletePassenger() {
        given()
                .when()
                .delete("/api/passenger/delete/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}

