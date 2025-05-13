package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
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
    @Container
    static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);
    @Container
    static final PostgreSQLContainer<?> postgresContainerForKeycloak = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("keycloak_db")
            .withUsername("keycloak")
            .withPassword("keycloak_password");

    @Container
    static final GenericContainer<?> keycloakContainer = new GenericContainer<>("quay.io/keycloak/keycloak:latest")
            .withExposedPorts(8080)
            .withEnv("DB_VENDOR", "postgres")
            .withEnv("DB_ADDR", postgresContainerForKeycloak.getHost())
            .withEnv("DB_DATABASE", "keycloak_db")
            .withEnv("DB_USERNAME", "keycloak")
            .withEnv("DB_PASSWORD", "keycloak_password")
            .withEnv("KEYCLOAK_USER", "admin")
            .withEnv("KEYCLOAK_PASSWORD", "admin")
            .withCommand("start-dev");


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
    private static final String BASE_URL = "/driver-service/api/v1/drivers";

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
                .get(BASE_URL+"/profile/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("name", equalTo(DRIVER_NAME));
    }
}

