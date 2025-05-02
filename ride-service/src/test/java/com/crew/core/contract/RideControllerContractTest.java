package com.crew.core.contract;

import com.crew.core.dto.RideDto;
import com.crew.core.entity.Ride;
import com.crew.core.repository.RideRepository;
import io.restassured.RestAssured;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RideControllerContractTest {
    @Container
    public static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @AfterAll
    static void tearDown(){
        mongoDBContainer.stop();
        kafkaContainer.stop();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private RideRepository rideRepository;
    private static final String BASE_URL="/ride-service/api/v1/rides";
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        Ride ride = new Ride(new ObjectId(), 23L, 43L, "Start Point", "End Point", "RESERVED", 0.0,System.currentTimeMillis() ,"PROMO123" );
        rideRepository.save(ride);
    }



    @Test
    public void testApplyPromoCode() {
        given()
                .queryParam("passengerId", "23")
                .queryParam("promoCode", "PROMO20")
                .when()
                .post(BASE_URL+"/applyPromoCode")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}

