package com.crew.core.unit.controller;

import com.crew.core.controller.RideController;
import com.crew.core.dto.RideDto;
import com.crew.core.service.RideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RideControllerUnitTest {

    @Mock
    private RideService rideService;

    @InjectMocks
    private RideController rideController;

    @Test
    public void testReserveRide() {
        RideDto rideDto = new RideDto();
        RideDto reservedRideDto = new RideDto();

        when(rideService.reserveRide(rideDto)).thenReturn(reservedRideDto);

        ResponseEntity<RideDto> response = rideController.reserveRide(rideDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservedRideDto, response.getBody());
        verify(rideService).reserveRide(rideDto);
    }

    @Test
    public void testStartRide() {
        String passengerId = "1";
        RideDto rideDto = new RideDto();

        when(rideService.startRide(Long.valueOf(passengerId))).thenReturn(rideDto);

        ResponseEntity<RideDto> response = rideController.startRide(passengerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rideDto, response.getBody());
        verify(rideService).startRide(Long.valueOf(passengerId));
    }

    @Test
    public void testEndRide() {
        String passengerId = "1";
        RideDto rideDto = new RideDto();

        when(rideService.endRide(Long.valueOf(passengerId))).thenReturn(rideDto);

        ResponseEntity<RideDto> response = rideController.endRide(passengerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rideDto, response.getBody());
        verify(rideService).endRide(Long.valueOf(passengerId));
    }
}