package com.example.core.integration;


import com.example.core.config.TestContainersConfig;
import com.example.core.dto.DriverDto;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
public class DriverControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DriverRepository driverRepository;

    private static final String DRIVER_NAME = "John Doe";
    private static final String LICENSE = "LICENSE123";
    private static final int RATING = 5;
    private static final Long DRIVER_ID=1L;
    private static final String BASE_URL="/drivers";
    @BeforeAll
    public static void initRestAssured(){
        RestAssured.baseURI="http://localhost";
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        driverRepository.save(new Driver(null, DRIVER_NAME, LICENSE, RATING));
    }

    @Test
    public void testRegisterDriver() {

        DriverDto driverDto = new DriverDto(null, DRIVER_NAME, LICENSE, RATING);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(driverDto)
                .when()
                .post(BASE_URL+"/register-and-send-event")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(DRIVER_NAME))
                .body("license_number", equalTo(LICENSE));
    }
    @Test
    public void testGetDriverProfile() {

        given()
                .when()
                .get(BASE_URL+"/profile/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("name", equalTo(DRIVER_NAME));
    }
}

