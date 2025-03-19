package com.example.core.unit.repository;

import com.example.core.PassengerApplicationTests;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PassengerRepositoryTest  {

    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


    @Autowired
    private PassengerRepository passengerRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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
