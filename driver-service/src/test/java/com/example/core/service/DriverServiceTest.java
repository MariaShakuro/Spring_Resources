package com.example.core.service;


import com.example.core.dto.DriverDto;
import com.example.core.dto.DriverMapper;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

    public DriverServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {

        DriverDto driverDto = new DriverDto(null, "John Doe", "LICENSE123", 5);
        Driver driver = new Driver(null, "John Doe", "LICENSE123", 5);
        Driver savedDriver = new Driver(1L, "John Doe", "LICENSE123", 5);
        DriverDto savedDriverDto = new DriverDto(1L, "John Doe", "LICENSE123", 5);

        when(driverMapper.toEntity(driverDto)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(savedDriver);
        when(driverMapper.toDto(savedDriver)).thenReturn(savedDriverDto);


        DriverDto result = driverService.register(driverDto);


        assertNotNull(result);
        assertEquals(savedDriverDto, result);
        verify(driverMapper).toEntity(driverDto);
        verify(driverRepository).save(driver);
        verify(driverMapper).toDto(savedDriver);
    }

    @Test
    public void testEditProfile() {

        Long driverId = 1L;
        DriverDto updateDto = new DriverDto(null, "Jane Doe", "LICENSE987", 5);
        Driver existingDriver = new Driver(1L, "John Doe", "LICENSE123", 5);
        Driver updatedDriver = new Driver(1L, "Jane Doe", "LICENSE987", 5);
        DriverDto updatedDriverDto = new DriverDto(1L, "Jane Doe", "LICENSE987", 5);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(existingDriver));
        doNothing().when(driverMapper).updateDriverFromDto(updateDto, existingDriver);
        when(driverRepository.save(existingDriver)).thenReturn(updatedDriver);
        when(driverMapper.toDto(updatedDriver)).thenReturn(updatedDriverDto);


        DriverDto result = driverService.editProfile(driverId, updateDto);


        assertNotNull(result);
        assertEquals(updatedDriverDto, result);
        verify(driverRepository).findById(driverId);
        verify(driverMapper).updateDriverFromDto(updateDto, existingDriver);
        verify(driverRepository).save(existingDriver);
        verify(driverMapper).toDto(updatedDriver);
    }

    @Test
    public void testGetDriverProfile() {

        Long driverId = 1L;
        Driver driver = new Driver(driverId, "John Doe", "LICENSE123", 5);
        DriverDto driverDto = new DriverDto(driverId, "John Doe", "LICENSE123", 5);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(driverMapper.toDto(driver)).thenReturn(driverDto);


        DriverDto result = driverService.getDriverProfile(driverId);


        assertNotNull(result);
        assertEquals(driverDto, result);
        verify(driverRepository).findById(driverId);
        verify(driverMapper).toDto(driver);
    }

    @Test
    public void testRateDriver() {

        Long driverId = 1L;
        int rating = 4;
        Driver driver = new Driver(driverId, "John Doe", "LICENSE123", 5);

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);


        driverService.rateDriver(driverId, rating);


        assertEquals(rating, driver.getRating());
        verify(driverRepository).findById(driverId);
        verify(driverRepository).save(driver);
    }

    @Test
    public void testDeleteDriver() {

        Long driverId = 1L;

        when(driverRepository.existsById(driverId)).thenReturn(true);
        doNothing().when(driverRepository).deleteById(driverId);


        driverService.deleteDriver(driverId);


        verify(driverRepository).existsById(driverId);
        verify(driverRepository).deleteById(driverId);
    }

}
