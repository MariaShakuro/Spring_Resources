package com.example.core.component;


import com.example.core.dto.DriverDto;
import com.example.core.service.DriverEventProducer;
import com.example.core.service.DriverService;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import static org.mockito.Mockito.*;
/*
@Testcontainers
@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverManagementSteps {

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

    @Mock
    private DriverService driverService;
    @InjectMocks
    private DriverEventProducer driverEventProducer;

    private DriverDto driverDto;

    public DriverManagementSteps() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("a driver with the following details:")
    public void aDriverWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMap(String.class, String.class);
        driverDto = new DriverDto(null, data.get("name"), data.get("licenseNumber"), Integer.parseInt(data.get("rating")));
    }

    @When("the driver registers")
    public void theDriverRegisters() {
        when(driverService.register(driverDto)).thenReturn(driverDto);
        driverService.register(driverDto);
        driverEventProducer.sendDriverEvent(driverDto.getId().toString());
    }

    @Then("the driver should be saved")
    public void theDriverShouldBeSaved() {
        verify(driverService, times(1)).register(driverDto);
        Assertions.assertNotNull(driverDto);
    }

    @Then("an event should be sent to the Kafka topic")
    public void anEventShouldBeSentToTheKafkaTopic() {
        verify(driverService, times(1)).register(driverDto);
    }
}
*/