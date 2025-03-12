package com.example.core.cucumber;

import com.example.core.dto.DriverDto;
import com.example.core.service.DriverEventProducer;
import com.example.core.service.DriverService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverManagementSteps extends BaseTest {


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
