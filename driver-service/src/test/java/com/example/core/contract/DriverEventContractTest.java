package com.example.core.contract;

import com.example.core.DriverApplication;
import com.example.core.service.DriverEventProducer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = DriverApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource("classpath:application-test.yaml")
@AutoConfigureMessageVerifier
@Import(MockConfig.class)
@ActiveProfiles("test")
public class DriverEventContractTest {
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
        registry.add("spring.kafka.bootstrap-servers", () -> String.format("%s:%d",
                kafkaContainer.getHost(), kafkaContainer.getMappedPort(KafkaContainer.KAFKA_PORT)));
    }

    @AfterAll
    static void tearDown() {
        kafkaContainer.stop();
        postgresContainer.stop();
    }
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

