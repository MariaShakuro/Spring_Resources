package com.example.core.unit.repository;

import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PassengerRepositoryUnitTest {
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
    private PassengerRepository passengerRepository;

    @Test
    public void testFindByEmail() {
        Passenger passenger = new Passenger(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        passenger = passengerRepository.save(passenger);

        Optional<Passenger> result = passengerRepository.findByEmail("alice@example.com");

        assertTrue(result.isPresent());
        assertEquals(passenger.getId(), result.get().getId());
        assertEquals(passenger.getName(), result.get().getName());
        assertEquals(passenger.getEmail(), result.get().getEmail());
        assertEquals(passenger.getPassword(), result.get().getPassword());
        assertEquals(passenger.getPhoneNumber(), result.get().getPhoneNumber());
        assertEquals(passenger.getPromocode(), result.get().getPromocode());
    }

    @Test
    public void testFindByEmail_NotFound() {
        Optional<Passenger> result = passengerRepository.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
    }
}
