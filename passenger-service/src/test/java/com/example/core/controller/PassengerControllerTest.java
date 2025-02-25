package com.example.core.controller;


import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
/*
public class PassengerControllerTest {

    @InjectMocks
    private PassengerController passengerController;

    @Mock
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterPassenger() {
        PassengerDto passengerDto = new PassengerDto();
        PassengerDto registeredPassengerDto = new PassengerDto();
        when(passengerService.registerPassenger(any(PassengerDto.class))).thenReturn(registeredPassengerDto);

        ResponseEntity<PassengerDto> response = passengerController.registerAndSendPassengerEvent(passengerDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredPassengerDto, response.getBody());
    }

    @Test
    void testGetPassengerByEmail() {
        Passenger passenger = new Passenger();
        when(passengerService.findPassengerByEmail(anyString())).thenReturn(Optional.of(passenger));

        ResponseEntity<Passenger> response = passengerController.getPassengerByEmail("test@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(passenger, response.getBody());
    }

    @Test
    void testGetPassengerByEmailNotFound() {
        when(passengerService.findPassengerByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Passenger> response = passengerController.getPassengerByEmail("test@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

   /* @Test
    void testUpdatePassenger() {
        Passenger passenger = new Passenger();
        Passenger updatedPassenger = new Passenger();
        when(passengerService.updatePassenger(any(Passenger.class))).thenReturn(updatedPassenger);

        ResponseEntity<Passenger> response = passengerController.updatePassenger(passenger);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPassenger, response.getBody());
    }*/
/*
    @Test
    void testDeletePassenger() {
        ResponseEntity<Void> response = passengerController.deletePassenger(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}*/
