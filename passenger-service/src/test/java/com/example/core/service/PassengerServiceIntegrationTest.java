package com.example.core.service;


import com.example.core.dto.PassengerDto;
import com.example.core.dto.PassengerMapper;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PassengerServiceIntegrationTest {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerMapper passengerMapper;

    @Test
    public void testRegisterPassenger() {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setEmail("test@example.com");
        passengerDto.setName("Test Name");
        passengerDto.setPassword("password");
        passengerDto.setPhoneNumber("1234567890");

        PassengerDto savedPassengerDto = passengerService.registerPassenger(passengerDto);

        Optional<Passenger> foundPassenger = passengerRepository.findByEmail("test@example.com");
        assertTrue(foundPassenger.isPresent());
        assertEquals("test@example.com", foundPassenger.get().getEmail());
        assertEquals("Test Name", foundPassenger.get().getName());
    }

    @Test
    public void testFindPassengerByEmail() {
        Passenger passenger = new Passenger();
        passenger.setEmail("test@example.com");
        passenger.setName("Test Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passengerRepository.save(passenger);

        Optional<Passenger> foundPassenger = passengerService.findPassengerByEmail("test@example.com");
        assertTrue(foundPassenger.isPresent());
        assertEquals("test@example.com", foundPassenger.get().getEmail());
        assertEquals("Test Name", foundPassenger.get().getName());
    }

    @Test
    public void testUpdatePassenger() {
        Passenger passenger = new Passenger();
        passenger.setEmail("testupdate@example.com");
        passenger.setName("Old Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        Passenger savedPassenger = passengerRepository.save(passenger);

        savedPassenger.setName("Updated Name");
        Passenger updatedPassenger = passengerService.updatePassenger(savedPassenger);

        Optional<Passenger> foundPassenger = passengerRepository.findById(updatedPassenger.getId());
        assertTrue(foundPassenger.isPresent());
        assertEquals("Updated Name", foundPassenger.get().getName());
    }

    @Test
    public void testDeletePassenger() {
        Passenger passenger = new Passenger();
        passenger.setEmail("testdelete@example.com");
        passenger.setName("Delete Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        Passenger savedPassenger = passengerRepository.save(passenger);

        passengerService.deletePassenger(savedPassenger.getId());

        Optional<Passenger> foundPassenger = passengerRepository.findById(savedPassenger.getId());
        assertTrue(foundPassenger.isEmpty());
    }
}

*/