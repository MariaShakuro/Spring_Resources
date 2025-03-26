package com.example.core.contract;

import com.example.core.PassengerApplication;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(classes = PassengerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PassengerProfileContractTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @DynamicPropertySource
    static void configureDatabaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private PassengerRepository passengerRepository;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        passengerRepository.deleteAll();
        Passenger passenger = new Passenger(1L, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        passengerRepository.save(passenger);
    }

    @Test
    public void testGetPassengerByEmail() {
        given()
                .when()
                .get("/api/passenger/alice@example.com")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Alice"))
                .body("email", equalTo("alice@example.com"))
                .body("promocode", equalTo("PROMO123"));
    }

    @Test
    public void testUpdatePassenger() {
        PassengerDto updateDto = new PassengerDto(null, "Alice Updated", "alice@example.com", "newpassword", "1234567890", "PROMO456");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateDto)
                .when()
                .put("/api/passenger/update/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Alice Updated"))
                .body("promocode", equalTo("PROMO456"));
    }
}

