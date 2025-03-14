package com.example.core.unit.controller;

import com.example.core.controller.DriverController;
import com.example.core.dto.DriverDto;
import com.example.core.service.DriverEventProducer;
import com.example.core.service.DriverService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DriverControllerTest {

    @InjectMocks
    private DriverController driverController;

    @Mock
    private DriverService driverService;

    @Mock
    private DriverEventProducer driverEventProducer;

    public DriverControllerTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetDriverProfile() {

        Long driverId = 1L;
        DriverDto driverDto = new DriverDto(driverId, "John Doe", "LICENSE123", 5);

        when(driverService.getDriverProfile(driverId)).thenReturn(driverDto);


        ResponseEntity<DriverDto> response = driverController.getDriverProfile(driverId);


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(driverDto, response.getBody());
        verify(driverService, times(1)).getDriverProfile(driverId);
    }

    @Test
    public void testRateDriver() {

        Long driverId = 1L;
        int rating = 5;

        doNothing().when(driverService).rateDriver(driverId, rating);


        ResponseEntity<Void> response = driverController.rateDriver(driverId, rating);


        assertEquals(200, response.getStatusCodeValue());
        verify(driverService, times(1)).rateDriver(driverId, rating);
    }

    @Test
    public void testDeleteDriver() {

        Long driverId = 1L;

        doNothing().when(driverService).deleteDriver(driverId);


        ResponseEntity<Void> response = driverController.deleteDriver(driverId);


        assertEquals(204, response.getStatusCodeValue());
        verify(driverService, times(1)).deleteDriver(driverId);
    }

}
