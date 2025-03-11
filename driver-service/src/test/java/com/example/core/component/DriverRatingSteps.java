package com.example.core.component;

import com.example.core.dto.DriverDto;
import com.example.core.service.DriverService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DriverRatingSteps {

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
