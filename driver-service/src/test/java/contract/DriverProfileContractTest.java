package contract;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
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

