package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.config.TestContainersConfig;
import com.example.core.service.DriverEventProducer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = DriverApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource("classpath:application-test.yaml")
@AutoConfigureMessageVerifier
@Import({MockConfig.class, TestContainersConfig.class})
@ActiveProfiles("test")
public class DriverEventContractTest {
    private DriverEventProducer driverEventProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @LocalServerPort
    private int port;

    private static final String DRIVER_ID_STRING="123";

    @BeforeAll
    public static void initRestAssured(){
        RestAssured.baseURI="http://localhost";
    }
    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        MockitoAnnotations.openMocks(this);
        driverEventProducer = new DriverEventProducer(kafkaTemplate);
    }

    @Test
    public void testSendDriverEvent() {
        driverEventProducer.sendDriverEvent(DRIVER_ID_STRING);

        verify(kafkaTemplate, times(1)).send("driver-events",DRIVER_ID_STRING);
        assertThat(DRIVER_ID_STRING).isNotNull();

    }
}

