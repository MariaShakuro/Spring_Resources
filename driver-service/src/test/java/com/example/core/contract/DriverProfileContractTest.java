package com.example.core.contract;

import com.example.core.DriverApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = DriverApplication.class)
@ActiveProfiles("test")
public class DriverProfileContractTest {

    @Test
    public void testGetDriverProfile() {
        RestAssured.baseURI = "http://localhost:8081";

        given()
                .when()
                .get("/drivers/profile/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("John Doe"));
    }
}

