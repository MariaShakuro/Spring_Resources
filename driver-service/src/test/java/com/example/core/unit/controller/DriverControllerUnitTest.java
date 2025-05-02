package com.example.core.unit.controller;

import com.example.core.controller.DriverController;
import com.example.core.dto.DriverDto;
import com.example.core.service.DriverEventProducer;
import com.example.core.service.DriverService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class DriverControllerUnitTest {

    @InjectMocks
    private DriverController driverController;

    @Mock
    private DriverService driverService;

    @Mock
    private DriverEventProducer driverEventProducer;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    private static final Long DRIVER_ID= (Long) 1L;
    private static final int RATING=5;

    @Test
    public void testGetDriverProfile() {

        DriverDto driverDto = new DriverDto(DRIVER_ID, "John Doe", "LICENSE123", 5);

        when(driverService.getDriverProfile(DRIVER_ID)).thenReturn(driverDto);

        ResponseEntity<DriverDto> response = driverController.getDriverProfile(DRIVER_ID);


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(driverDto, response.getBody());
        verify(driverService, times(1)).getDriverProfile(DRIVER_ID);
    }

    @Test
    public void testRateDriver() {

        doNothing().when(driverService).rateDriver(DRIVER_ID,RATING);


        ResponseEntity<Void> response = driverController.rateDriver(DRIVER_ID,RATING);


        assertEquals(200, response.getStatusCodeValue());
        verify(driverService, times(1)).rateDriver(DRIVER_ID,RATING);
    }

    @Test
    public void testDeleteDriver() {



        doNothing().when(driverService).deleteDriver(DRIVER_ID);


        ResponseEntity<Void> response = driverController.deleteDriver(DRIVER_ID);


        assertEquals(204, response.getStatusCodeValue());
        verify(driverService, times(1)).deleteDriver(DRIVER_ID);
    }

}
