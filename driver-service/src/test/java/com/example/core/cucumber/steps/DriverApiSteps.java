package com.example.core.cucumber.steps;

import com.example.core.DriverApplication;
import com.example.core.dto.DriverDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Component
public class DriverApiSteps {

    private Response response;

    @LocalServerPort
    private int port;
    @Given("the Driver service is running")
    public void theDriverServiceIsRunning() {
        RestAssured.baseURI = "http://localhost:" + port;
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
    public void the_system_has_a_driver_with_id(String id) {
        Long driverId = Long.valueOf(id);
        DriverDto driverDto = new DriverDto(driverId, "John Doe", "LICENSE123", 5);
        given()
                .contentType("application/json")
                .body(driverDto)
                .post("/drivers/register-and-send-event")
                .then()
                .statusCode(200);
    }

    @When("I send a POST request to {string} with the following data:")
    public void iSendAPostRequestToWithTheFollowingData(String endpoint, Map<String, String> data) {
        response = given()
                .contentType("application/json")
                .body(data)
                .post(endpoint);
    }

    @When("I send a PUT request to {string} with the following data:")
    public void i_send_a_put_request_to_with_the_following_data(String endpoint, Map<String, String> data) {
        response = given()
                .contentType("application/json")
                .body(data)
                .put(endpoint);
    }

    @When("I send a DELETE request to {string}")
    public void i_send_a_delete_request_to(String endpoint) {
        response = given()
                .when()
                .delete(endpoint);
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String endpoint) {
        response = given()
                .when()
                .post(endpoint);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response body should contain:")
    public void theResponseBodyShouldContain(Map<String, String> expectedBody) {
        expectedBody.forEach((key, value) -> response.then().body(key, equalTo(value)));
    }

    @Then("the response body should contain an error message")
    public void the_response_body_should_contain_an_error_message() {
        response.then().body("error", notNullValue());
    }
}
