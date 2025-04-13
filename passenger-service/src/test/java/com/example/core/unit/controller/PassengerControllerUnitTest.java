package com.example.core.unit.controller;

import com.example.core.controller.PassengerController;
import com.example.core.dto.PassengerDto;
import com.example.core.service.PassengerEventProducer;
import com.example.core.service.PassengerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PassengerControllerUnitTest {
    @InjectMocks
    private PassengerController passengerController;

    @Mock
    private PassengerService passengerService;

    @Mock
    private PassengerEventProducer passengerEventProducer;

    private PassengerDto testPassengerDto;
    private PassengerDto registeredPassengerDto;
    private PassengerDto updatePassengerDto;
    private PassengerDto updatedPassengerDto;
    private String testEmail;

    @BeforeEach
    public void setup() {
        testPassengerDto = new PassengerDto(null, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        registeredPassengerDto = new PassengerDto(1L, "Alice", "alice@example.com", "password123", "1234567890", "PROMO123");
        updatePassengerDto = new PassengerDto(1L, "Charlie", "charlie@example.com", "password123", "1234567890", "PROMO789");
        updatedPassengerDto = new PassengerDto(1L, "Charlie", "newcharlie@example.com", "newpassword", "0987654321", "PROMO123");
        testEmail = "alice@example.com";
    }

    @Test
    public void testRegisterAndSendPassengerEvent() {
        when(passengerService.registerPassenger(testPassengerDto)).thenReturn(registeredPassengerDto);

        ResponseEntity<PassengerDto> response = passengerController.registerAndSendPassengerEvent(testPassengerDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredPassengerDto, response.getBody());

        verify(passengerService).registerPassenger(testPassengerDto);
        verify(passengerEventProducer).sendPassengerEvent("1");
    }


    @Test
    public void testUpdatePassenger() {
        when(passengerService.updatePassenger(updatePassengerDto)).thenReturn(updatedPassengerDto);

        ResponseEntity<PassengerDto> response = passengerController.updatePassenger(updatePassengerDto.getId(), updatePassengerDto);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(updatedPassengerDto, response.getBody());
        verify(passengerService, times(1)).updatePassenger(updatePassengerDto);
    }
}