package com.example.core.cucumber;

import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
/*
@DirtiesContext
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverApiSteps extends BaseTest {


    private Response response;

    @LocalServerPort
    private int port;

    @Autowired
    private DriverRepository driverRepository;

    @BeforeEach
    public void setup() {
        // Set up RestAssured for API testing
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";


        // Clear database and add initial test data
        driverRepository.deleteAll(); // Ensure a clean database state
        Driver driver = new Driver(null, "John Doe", "LICENSE123", 5);

    }
        @Given("the Driver service is running")
    public void theDriverServiceIsRunning() {
            System.out.println("Driver service is running on port: " + port);
    }

    @Given("the system has no driver with license number {string}")
    public void theSystemHasNoDriverWithLicenseNumber(String licenseNumber) {
        Response response = given()
                .when()
                .get("/drivers/profile?licenseNumber=" + licenseNumber);

        if (response.getStatusCode() == 200) {
            Long id = response.jsonPath().getLong("id");
            given()
                    .when()
                    .delete("/drivers/delete/" + id)
                    .then()
                    .statusCode(204);
        }
    }

    @Given("the system has a driver with ID {string}")
    public void theSystemHasADriverWithID(String id) {
        Long driverId = Long.valueOf(id);
        Map<String, Object> driverData = Map.of(
                "id", driverId,
                "name", "John Doe",
                "licenseNumber", "LICENSE123",
                "rating", 5
        );

        given()
                .contentType("application/json")
                .body(driverData)
                .post("/drivers/register-and-send-event")
                .then()
                .statusCode(200);
    }

    @When("I send a POST request to {string} with the following data:")
    public void iSendAPostRequestToWithTheFollowingData(String endpoint, DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        response = given()
                .contentType("application/json")
                .body(data)
                .post(endpoint);
    }

    @When("I send a PUT request to {string} with the following data:")
    public void iSendAPutRequestToWithTheFollowingData(String endpoint, DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        response = given()
                .contentType("application/json")
                .body(data)
                .put(endpoint);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADeleteRequestTo(String endpoint) {
        response = given()
                .when()
                .delete(endpoint);
    }

    @When("I send a POST request to {string}")
    public void iSendAPostRequestTo(String endpoint) {
        response = given()
                .when()
                .post(endpoint);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response body should contain:")
    public void theResponseBodyShouldContain(DataTable dataTable) {
        Map<String, String> expectedBody = dataTable.asMap(String.class, String.class);

        expectedBody.forEach((key, value) ->
                response.then().body(key, equalTo(value))
        );
    }
}
*/