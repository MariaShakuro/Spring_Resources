package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.dto.DriverDto;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import io.restassured.RestAssured;
import org.junit.Before;
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
@SpringBootTest(classes = DriverApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DriverProfileContractTest {

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
    private DriverRepository driverRepository;
    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        Driver driver = new Driver(null, "John Doe", "LICENSE123", 5);
        driverRepository.save(driver);
    }
    @Test
    public void testGetDriverProfile() {
        given()
                .when()
                .get("/drivers/profile/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("John Doe"));
    }
}

