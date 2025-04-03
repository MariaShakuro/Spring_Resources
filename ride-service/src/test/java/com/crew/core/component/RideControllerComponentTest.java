package com.crew.core.component;

import com.crew.core.dto.RideDto;
import com.crew.core.entity.Ride;
import com.crew.core.repository.RideRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "eureka.client.registerWithEureka=false"
})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class RideControllerComponentTest {

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RideRepository rideRepository;

    private static final String BASE_URL = "/api/rides";
    private RideDto testRide;

    @BeforeEach
    void setup() {

        testRide = new RideDto(null, "1", "2","Minsk","Moscow","COMPLETED", 100.0,1L,"PROMOCODE123");
        Ride ride = new Ride("1", "passenger123", "driver123",
                "Start Point", "End Point",
                "RESERVED", 50.0,
                System.currentTimeMillis(),
                "PROMO123");
        rideRepository.save(ride);
    }


    @Test
    void shouldGetRideHistoryForPassenger() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/history")
                        .queryParam("passengerId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRideHistoryForDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/history")
                        .queryParam("driverId", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldApplyPromoCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/applyPromoCode")
                        .queryParam("passengerId", "1")
                        .queryParam("promoCode", "DISCOUNT10"))
                .andExpect(status().isOk());
    }
}