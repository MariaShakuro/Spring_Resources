package com.example.core.contract;

import com.example.core.PassengerApplication;
import com.example.core.config.TestContainersConfig;
import com.example.core.repository.PassengerRepository;
import com.example.core.service.PassengerEventProducer;
import com.example.core.service.PassengerService;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Testcontainers
@SpringBootTest(classes = PassengerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource("classpath:application-test.yaml")
@AutoConfigureMessageVerifier
@Import({MockConfig.class, TestContainersConfig.class})
@ActiveProfiles("test")
public class PassengerEventContractTest {

    private PassengerEventProducer passengerEventProducer;

    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        MockitoAnnotations.openMocks(this);
        passengerEventProducer = new PassengerEventProducer(kafkaTemplate);
    }

    @Test
    public void testSendPassengerEvent() {
        String passengerId = "123";
        passengerEventProducer.sendPassengerEvent(passengerId);

        verify(kafkaTemplate, times(1)).send("passenger-events", passengerId);
        Assertions.assertThat(passengerId).isNotNull();
    }
}
