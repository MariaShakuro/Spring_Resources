package com.example.core.controller;

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
<<<<<<< Updated upstream
    void testRegisterDriver() {
        DriverDto driverDto = new DriverDto();
        DriverDto savedDriverDto = new DriverDto();
        when(driverService.register(any(DriverDto.class))).thenReturn(savedDriverDto);
=======
    public void testRegisterAndSendDriverEvent() {
>>>>>>> Stashed changes

        DriverDto driverDto = new DriverDto(null, "John Doe", "LICENSE123", 5);
        DriverDto savedDriverDto = new DriverDto(1L, "John Doe", "LICENSE123", 5);

        when(driverService.register(driverDto)).thenReturn(savedDriverDto);


        ResponseEntity<DriverDto> response = driverController.registerAndSendDriverEvent(driverDto);


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedDriverDto, response.getBody());
        verify(driverService, times(1)).register(driverDto);
        verify(driverEventProducer, times(1)).sendDriverEvent(savedDriverDto.getId().toString());
    }
<<<<<<< Updated upstream

    @Test
    void testEditProfile() {
        DriverDto driverDto = new DriverDto();
        DriverDto updatedDriverDto = new DriverDto();
        when(driverService.editProfile(anyLong(), any(DriverDto.class))).thenReturn(updatedDriverDto);
=======
    @Test
    public void testEditProfile() {
>>>>>>> Stashed changes

        Long driverId = 1L;
        DriverDto updatedDriverDto = new DriverDto(driverId, "Jane Doe", "LICENSE456", 4);

        when(driverService.editProfile(eq(driverId), any(DriverDto.class))).thenReturn(updatedDriverDto);


        ResponseEntity<?> response = driverController.editProfile(driverId, updatedDriverDto);


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedDriverDto, response.getBody());
        verify(driverService, times(1)).editProfile(driverId, updatedDriverDto);
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
