package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.config.TestContainersConfig;
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
@Import(TestContainersConfig.class)
public class DriverProfileContractTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DriverRepository driverRepository;
    private static final String DRIVER_NAME="John Doe";
    private static final String LICENSE="LICENSE123";
    private static final int RATING =5;
    @BeforeAll
    public static void initRestAssured(){
        RestAssured.baseURI="http://localhost";
    }
    @BeforeEach
    public void setup(){
        RestAssured.port = port;

        Driver driver = new Driver(null, DRIVER_NAME, LICENSE, RATING);
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
                .body("name", equalTo(DRIVER_NAME));
    }
}

