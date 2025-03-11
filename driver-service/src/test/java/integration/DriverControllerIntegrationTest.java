package integration;

import com.example.core.dto.DriverDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverControllerIntegrationTest {

    @Test
    public void testRegisterDriver() {
        RestAssured.baseURI = "http://localhost:8081";

        DriverDto driverDto = new DriverDto(null, "John Doe", "LICENSE123", 5);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(driverDto)
                .when()
                .post("/drivers/register-and-send-event")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("John Doe"))
                .body("license_number", equalTo("LICENSE123"));
    }

    @Test
    public void testGetDriverProfile() {
        RestAssured.baseURI = "http://localhost:8081";

        given()
                .when()
                .get("/drivers/profile/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("name", equalTo("John Doe"));
    }
}

