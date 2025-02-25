package com.example.core.repository;

import com.example.core.entity.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    public void testFindByEmail() {
        Passenger passenger = new Passenger();
        passenger.setEmail("test@example.com");
        passenger.setName("Test Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passengerRepository.save(passenger);

        Optional<Passenger> foundPassenger = passengerRepository.findByEmail("test@example.com");
        assertTrue(foundPassenger.isPresent());
        assertEquals("test@example.com", foundPassenger.get().getEmail());
    }

    @Test
    public void testSavePassenger() {
        Passenger passenger = new Passenger();
        passenger.setEmail("save@example.com");
        passenger.setName("Save Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");

        Passenger savedPassenger = passengerRepository.save(passenger);
        Optional<Passenger> foundPassenger = passengerRepository.findById(savedPassenger.getId());

        assertTrue(foundPassenger.isPresent());
        assertEquals("save@example.com", foundPassenger.get().getEmail());
    }

    @Test
    public void testDeletePassenger() {
        Passenger passenger = new Passenger();
        passenger.setEmail("delete@example.com");
        passenger.setName("Delete Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passengerRepository.save(passenger);

        passengerRepository.delete(passenger);
        Optional<Passenger> foundPassenger = passengerRepository.findByEmail("delete@example.com");

        assertTrue(foundPassenger.isEmpty());
    }
}
*/