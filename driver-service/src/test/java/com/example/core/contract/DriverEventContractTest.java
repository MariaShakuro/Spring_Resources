package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.repository.DriverRepository;
import com.example.core.service.DriverEventProducer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = DriverApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})

@AutoConfigureMessageVerifier
@Import(MockConfig.class)
@ActiveProfiles("test")
public class DriverEventContractTest {

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    );
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Kafka properties
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    private DriverEventProducer driverEventProducer;

    @Mock
    private DriverRepository driverRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        MockitoAnnotations.openMocks(this);
        driverEventProducer = new DriverEventProducer(kafkaTemplate);
    }

    @Test
    public void testSendDriverEvent() {
        String driverId = "123";
        driverEventProducer.sendDriverEvent(driverId);

        verify(kafkaTemplate, times(1)).send("driver-events", driverId);

        assertThat(driverId).isNotNull();
    }
}

