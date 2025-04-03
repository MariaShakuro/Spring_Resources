package com.example.core.component;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class KafkaProducerConfigComponentTest {
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
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testKafkaTemplateNotNull() {
        assertNotNull(kafkaTemplate);
    }

    @Test
    public void testSendMessage() {
        kafkaTemplate.send("passenger-events", "Test message");
    }
}

