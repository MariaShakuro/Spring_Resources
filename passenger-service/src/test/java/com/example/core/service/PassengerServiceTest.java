package com.example.core.service;


import com.example.core.dto.PassengerDto;
import com.example.core.dto.PassengerMapper;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*
public class PassengerServiceTest {

    @InjectMocks
    private PassengerService passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterPassenger() {
        PassengerDto passengerDto = new PassengerDto();
        Passenger passenger = new Passenger();
        Passenger savedPassenger = new Passenger();
        PassengerDto savedPassengerDto = new PassengerDto();

        when(passengerMapper.toEntity(any(PassengerDto.class))).thenReturn(passenger);
        when(passengerRepository.save(any(Passenger.class))).thenReturn(savedPassenger);
        when(passengerMapper.toDto(any(Passenger.class))).thenReturn(savedPassengerDto);

        PassengerDto result = passengerService.registerPassenger(passengerDto);

        verify(passengerMapper).toEntity(passengerDto);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toDto(savedPassenger);
        assertEquals(savedPassengerDto, result);
    }

    @Test
    void testFindPassengerByEmail() {
        Passenger passenger = new Passenger();
        when(passengerRepository.findByEmail(anyString())).thenReturn(Optional.of(passenger));

        Optional<Passenger> result = passengerService.findPassengerByEmail("test@example.com");

        verify(passengerRepository).findByEmail("test@example.com");
        assertEquals(Optional.of(passenger), result);
    }

    @Test
    void testUpdatePassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setEmail("testupdate@example.com");
        passenger.setName("Old Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");


        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(1L);
        passengerDto.setName("Updated Name");


        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setId(1L);
        updatedPassenger.setEmail("testupdate@example.com");
        updatedPassenger.setName("Updated Name");
        updatedPassenger.setPassword("password");
        updatedPassenger.setPhoneNumber("1234567890");


        PassengerDto updatedPassengerDto = new PassengerDto();
        updatedPassengerDto.setId(1L);
        updatedPassengerDto.setEmail("testupdate@example.com");
        updatedPassengerDto.setName("Updated Name");
        updatedPassengerDto.setPassword("password");
        updatedPassengerDto.setPhoneNumber("1234567890");


        when(passengerRepository.findById(anyLong())).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(updatedPassenger);
        when(passengerMapper.toDto(any(Passenger.class))).thenReturn(updatedPassengerDto);


        PassengerDto result = passengerService.updatePassenger(passengerDto);


        verify(passengerRepository).findById(1L);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toDto(updatedPassenger);


        assertEquals("Updated Name", result.getName());
    }



    @Test
    void testDeletePassenger() {
        Long passengerId = 1L;
        passengerService.deletePassenger(passengerId);

        verify(passengerRepository).deleteById(passengerId);
    }
}
*/