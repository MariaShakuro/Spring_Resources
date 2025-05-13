package com.example.core.integration;


import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
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
@Transactional
public class PassengerControllerIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @Container
    static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    ).withExposedPorts(9093);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.kafka.bootstrap-servers", () -> String.format("%s:%d",
                kafkaContainer.getHost(), kafkaContainer.getMappedPort(KafkaContainer.KAFKA_PORT)));
    }

    @AfterAll
    static void tearDown() {
        kafkaContainer.stop();
        postgresContainer.stop();
    }
    @LocalServerPort
    private int port;

    @Autowired
    private PassengerRepository passengerRepository;
    private static final String BASE_URL = "/passenger-service/api/v1/passenger";
    private Long passengerId;
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
      //  passengerRepository.deleteByEmail("alice@example.com");
         Passenger savedPassenger=passengerRepository.save(new Passenger(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123"));
    }

    @Test
    public void testRegisterPassenger() {
        PassengerDto passengerDto = new PassengerDto(null, "Bob", "bob@example.com", "password456", "0987654321", "PROMO456");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passengerDto)
                .when()
                .post(BASE_URL+"/register-and-send-event")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo(passengerDto.getName()))
                .body("email", equalTo(passengerDto.getEmail()))
                .body("promocode", equalTo(passengerDto.getPromocode()));
    }


    @Test
    public void testDeletePassenger() {
        given()
                .when()
                .delete(BASE_URL+"/delete/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}

