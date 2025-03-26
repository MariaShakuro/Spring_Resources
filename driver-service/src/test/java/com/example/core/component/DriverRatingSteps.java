package com.example.core.component;


import com.example.core.dto.DriverDto;
import com.example.core.service.DriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
@Testcontainers
@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverRatingSteps  {


    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("dsova2207_12");

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

    @Autowired
    private DriverService driverService;

    private Long driverId;
    private int rating;

    @Given("a driver with ID {long} exists")
    public void aDriverWithIDExists(Long id) {
        driverId = id;

        // Предварительно создаем водителя для теста
        DriverDto driverDto = new DriverDto(id, "Jane Doe", "LICENSE987", 0);
        driverService.register(driverDto);
    }

    @When("the driver is rated with a value of {int}")
    public void theDriverIsRatedWithValueOf(int rateValue) {
        rating = rateValue;
        driverService.rateDriver(driverId, rating);
    }

    @Then("the driver's rating should be updated to {int}")
    public void theDriversRatingShouldBeUpdatedTo(int expectedRating) {
        DriverDto updatedDriver = driverService.getDriverProfile(driverId);
        assertEquals(expectedRating, updatedDriver.getRating());
    }
}
*/