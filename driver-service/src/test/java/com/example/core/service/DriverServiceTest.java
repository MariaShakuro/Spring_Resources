package com.example.core.service;


import com.example.core.dto.DriverDto;
import com.example.core.dto.DriverMapper;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*
public class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        DriverDto driverDto = new DriverDto();
        Driver driver = new Driver();
        Driver savedDriver = new Driver();
        DriverDto savedDriverDto = new DriverDto();

        when(driverMapper.toEntity(any(DriverDto.class))).thenReturn(driver);
        when(driverRepository.save(any(Driver.class))).thenReturn(savedDriver);
        when(driverMapper.toDto(any(Driver.class))).thenReturn(savedDriverDto);

        DriverDto result = driverService.register(driverDto);

        verify(driverMapper).toEntity(driverDto);
        verify(driverRepository).save(driver);
        verify(driverMapper).toDto(savedDriver);
        assertEquals(savedDriverDto, result);
    }

    @Test
    void testEditProfile() {
        DriverDto driverDto = new DriverDto();
        Driver existedDriver = new Driver();
        Driver updatedDriver = new Driver();
        DriverDto updatedDriverDto = new DriverDto();

        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(existedDriver));
        when(driverRepository.save(any(Driver.class))).thenReturn(updatedDriver);
        when(driverMapper.toDto(any(Driver.class))).thenReturn(updatedDriverDto);

        DriverDto result = driverService.editProfile(1L, driverDto);

        verify(driverRepository).findById(1L);
        verify(driverMapper).updateDriverFromDto(driverDto, existedDriver);
        verify(driverRepository).save(existedDriver);
        verify(driverMapper).toDto(updatedDriver);
        assertEquals(updatedDriverDto, result);
    }

    @Test
    void testGetDriverProfile() {
        Driver driver = new Driver();
        DriverDto driverDto = new DriverDto();

        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
        when(driverMapper.toDto(any(Driver.class))).thenReturn(driverDto);

        DriverDto result = driverService.getDriverProfile(1L);

        verify(driverRepository).findById(1L);
        verify(driverMapper).toDto(driver);
        assertEquals(driverDto, result);
    }

    @Test
    void testRateDriver() {
        Driver driver = new Driver();

        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));

        driverService.rateDriver(1L, 5);

        verify(driverRepository).findById(1L);
        driver.setRating(5);
        verify(driverRepository).save(driver);
    }

    @Test
    void testDeleteDriver() {
        when(driverRepository.existsById(anyLong())).thenReturn(true);

        driverService.deleteDriver(1L);

        verify(driverRepository).existsById(1L);
        verify(driverRepository).deleteById(1L);
    }
}
*/