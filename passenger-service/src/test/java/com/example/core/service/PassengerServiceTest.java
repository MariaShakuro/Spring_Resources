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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        Passenger updatedPassenger = new Passenger();
        when(passengerRepository.save(any(Passenger.class))).thenReturn(updatedPassenger);

        Passenger result = passengerService.updatePassenger(passenger);

        verify(passengerRepository).save(passenger);
        assertEquals(updatedPassenger, result);
    }

    @Test
    void testDeletePassenger() {
        Long passengerId = 1L;
        passengerService.deletePassenger(passengerId);

        verify(passengerRepository).deleteById(passengerId);
    }
}
*/