package com.example.core.contract;

import com.example.core.PassengerApplication;
import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import io.restassured.RestAssured;
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
@SpringBootTest(classes = PassengerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PassengerProfileContractTest {
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
    private static final String BASE_URL = "/api/passenger";
    private static final Passenger TEST_PASSENGER = new Passenger(
            null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123"
    );
    @BeforeEach
    public void setup() {
            RestAssured.baseURI = "http://localhost";
            RestAssured.port = port;

            passengerRepository.deleteAll();
            passengerRepository.flush();

            passengerRepository.save(new Passenger(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123"));

    }

   /* @Test
    public void testGetPassengerByEmail() {
        given()
                .when()
                .get(BASE_URL+"/alice@example.com")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(TEST_PASSENGER.getName()))
                .body("email", equalTo(TEST_PASSENGER.getEmail()))
                .body("promocode", equalTo(TEST_PASSENGER.getPromocode()));
    }*/

    @Test
    public void testUpdatePassenger() {
        PassengerDto updateDto = new PassengerDto(null, "Alice Updated", "alice@example.com", "newpassword", "1234567890", "PROMO456");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateDto)
                .when()
                .put(BASE_URL+"/update/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(updateDto.getName()))
                .body("promocode", equalTo(updateDto.getPromocode()));
    }
}

