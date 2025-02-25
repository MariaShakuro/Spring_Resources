package com.example.core.controller;



import com.example.core.dto.DriverDto;
import com.example.core.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*
public class DriverControllerTest {

    @InjectMocks
    private DriverController driverController;

    @Mock
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterDriver() {
        DriverDto driverDto = new DriverDto();
        DriverDto savedDriverDto = new DriverDto();
        when(driverService.register(any(DriverDto.class))).thenReturn(savedDriverDto);

        ResponseEntity<DriverDto> response = driverController.registerDriver(driverDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedDriverDto, response.getBody());
    }

    @Test
    void testEditProfile() {
        DriverDto driverDto = new DriverDto();
        DriverDto updatedDriverDto = new DriverDto();
        when(driverService.editProfile(anyLong(), any(DriverDto.class))).thenReturn(updatedDriverDto);

        ResponseEntity<?> response = driverController.editProfile(1L, driverDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDriverDto, response.getBody());
    }

    @Test
    void testGetDriverProfile() {
        DriverDto driverDto = new DriverDto();
        when(driverService.getDriverProfile(anyLong())).thenReturn(driverDto);

        ResponseEntity<DriverDto> response = driverController.getDriverProfile(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverDto, response.getBody());
    }

    @Test
    void testRateDriver() {
        ResponseEntity<Void> response = driverController.rateDriver(1L, 5);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(driverService).rateDriver(1L, 5);
    }

    @Test
    void testDeleteDriver() {
        ResponseEntity<Void> response = driverController.deleteDriver(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(driverService).deleteDriver(1L);
    }
}
*/