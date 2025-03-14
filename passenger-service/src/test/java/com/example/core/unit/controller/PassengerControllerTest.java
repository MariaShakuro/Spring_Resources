package com.example.core.unit.controller;

import com.example.core.controller.PassengerController;
import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.service.PassengerEventProducer;
import com.example.core.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PassengerControllerTest {

    @InjectMocks
    private PassengerController passengerController;

    @Mock
    private PassengerService passengerService;

    @Mock
    private PassengerEventProducer passengerEventProducer;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterAndSendPassengerEvent() {
        PassengerDto passengerDto = new PassengerDto(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        PassengerDto registeredPassengerDto = new PassengerDto(1L, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");

        when(passengerService.registerPassenger(passengerDto)).thenReturn(registeredPassengerDto);

        ResponseEntity<PassengerDto> response = passengerController.registerAndSendPassengerEvent(passengerDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(registeredPassengerDto, response.getBody());
        verify(passengerService, times(2)).registerPassenger(passengerDto);
        verify(passengerEventProducer, times(1)).sendPassengerEvent("1");
    }

    @Test
    public void testGetPassengerByEmail() {
        String email = "bob@example.com";
        Passenger passenger = new Passenger(1L, "Bob", email, "password123", "9876543210", "PROMO456");

        when(passengerService.findPassengerByEmail(email)).thenReturn(Optional.of(passenger));

        ResponseEntity<Passenger> response = passengerController.getPassengerByEmail(email);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(passenger, response.getBody());
        verify(passengerService, times(1)).findPassengerByEmail(email);
    }

    @Test
    public void testUpdatePassenger() {
        Long id = 1L;
        PassengerDto passengerDto = new PassengerDto(id, "Charlie", "charlie@example.com", "password123", "1234567890", "PROMO789");
        PassengerDto updatedPassengerDto = new PassengerDto(id, "Charlie", "newcharlie@example.com", "newpassword", "0987654321", "PROMO123");

        when(passengerService.updatePassenger(passengerDto)).thenReturn(updatedPassengerDto);

        ResponseEntity<PassengerDto> response = passengerController.updatePassenger(id, passengerDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedPassengerDto, response.getBody());
        verify(passengerService, times(1)).updatePassenger(passengerDto);
    }
}
