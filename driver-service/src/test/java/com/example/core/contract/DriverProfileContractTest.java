package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
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
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



@SpringBootTest(classes = DriverApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class DriverProfileContractTest {

    @Container
    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");


    @Container
    static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");

        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @AfterAll
    static void tearDown() {
        kafkaContainer.stop();
        postgresContainer.stop();
    }
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

