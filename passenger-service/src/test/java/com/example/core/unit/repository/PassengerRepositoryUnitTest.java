package com.example.core.unit.repository;

import com.example.core.config.TestContainersConfig;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
public class PassengerRepositoryUnitTest {

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
