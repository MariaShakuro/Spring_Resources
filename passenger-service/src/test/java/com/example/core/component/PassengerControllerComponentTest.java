package com.example.core.component;


import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
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
@Transactional
public class PassengerControllerComponentTest {

    @Container
    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @Container
    static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    ).withExposedPorts(9093);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.kafka.bootstrap-servers", () -> String.format("%s:%d",
                kafkaContainer.getHost(), kafkaContainer.getMappedPort(KafkaContainer.KAFKA_PORT)));
    }

    @AfterAll
    static void tearDown() {
        kafkaContainer.stop();
        postgresContainer.stop();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerRepository passengerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BASE_URL = "/api/passenger";

    @BeforeEach
    void setUp() {
     //   passengerRepository.deleteByEmail("john@example.com");
    }

    @Test
    public void testRegisterPassenger() throws Exception {
        PassengerDto testPassenger = new PassengerDto(null, "John Doe", "john@example.com", "password", "1234567890", "PROMO123");

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register-and-send-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPassenger)))
                .andExpect(status().isOk());
    }

  /*  @Test
    public void testGetPassengerByEmail() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setName("John Doe");
        passenger.setEmail("john@example.com");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passenger.setPromocode("PROMO123");
        passengerRepository.save(passenger);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/john@example.com"))
                .andExpect(status().isOk());
    }*/
}

