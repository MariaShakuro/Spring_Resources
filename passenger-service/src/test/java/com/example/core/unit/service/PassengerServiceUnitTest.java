package com.example.core.unit.service;


import com.example.core.dto.PassengerDto;
import com.example.core.dto.PassengerMapper;
import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import com.example.core.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PassengerServiceUnitTest {

    @InjectMocks
    private PassengerService passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterPassenger() {

        PassengerDto passengerDto = new PassengerDto(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        Passenger passenger = new Passenger(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        Passenger savedPassenger = new Passenger(1L, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        PassengerDto savedPassengerDto = new PassengerDto(1L, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");


        when(passengerMapper.toEntity(passengerDto)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(savedPassenger);
        when(passengerMapper.toDto(savedPassenger)).thenReturn(savedPassengerDto);


        PassengerDto result = passengerService.registerPassenger(passengerDto);

        assertNotNull(result);
        assertEquals(savedPassengerDto, result);
        verify(passengerMapper).toEntity(passengerDto);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).toDto(savedPassenger);
    }


}

