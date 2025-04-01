package com.example.core.unit.service;


import com.example.core.dto.DriverDto;
import com.example.core.dto.DriverMapper;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import com.example.core.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DriverServiceUnitTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;
    private static final Long DRIVER_ID=1L;
    private static final String LICENSE="LICENSE123";
    private static final String NAME="John Doe";
    private static final int RATING=5;
    private static final int NEW_DRIVER_RATING=4;

    private DriverDto driverDto;
    private Driver driver;

    @BeforeEach
    public void setup(){
        driverDto=new DriverDto(null,NAME,LICENSE,RATING);
        driver=new Driver(null,NAME,LICENSE,RATING);
    }

    @Test
    public void testRegister() {

        Driver savedDriver = new Driver(DRIVER_ID, NAME, LICENSE, RATING);
        DriverDto savedDriverDto = new DriverDto(DRIVER_ID, NAME, LICENSE, RATING);
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
        DriverDto updateDto = new DriverDto(null, NAME, LICENSE, RATING);
        Driver existingDriver = new Driver(DRIVER_ID, NAME, LICENSE, RATING);
        Driver updatedDriver = new Driver(DRIVER_ID, NAME, "LICENSE987", RATING);
        DriverDto updatedDriverDto = new DriverDto(DRIVER_ID, NAME, "LICENSE987", RATING);

        when(driverRepository.findById(DRIVER_ID)).thenReturn(Optional.of(existingDriver));
        doNothing().when(driverMapper).updateDriverFromDto(updateDto, existingDriver);
        when(driverRepository.save(existingDriver)).thenReturn(updatedDriver);
        when(driverMapper.toDto(updatedDriver)).thenReturn(updatedDriverDto);

        DriverDto result = driverService.editProfile(DRIVER_ID, updateDto);

        assertNotNull(result);
        assertEquals(updatedDriverDto, result);
        verify(driverRepository).findById(DRIVER_ID);
        verify(driverMapper).updateDriverFromDto(updateDto, existingDriver);
        verify(driverRepository).save(existingDriver);
        verify(driverMapper).toDto(updatedDriver);
    }

    @Test
    public void testGetDriverProfile() {
        Driver driver = new Driver(DRIVER_ID, NAME, LICENSE, RATING);
        DriverDto driverDto = new DriverDto(DRIVER_ID, NAME, LICENSE, RATING);

        when(driverRepository.findById(DRIVER_ID)).thenReturn(Optional.of(driver));
        when(driverMapper.toDto(driver)).thenReturn(driverDto);

        DriverDto result = driverService.getDriverProfile(DRIVER_ID);

        assertNotNull(result);
        assertEquals(driverDto, result);
        verify(driverRepository).findById(DRIVER_ID);
        verify(driverMapper).toDto(driver);
    }

    @Test
    public void testRateDriver() {
        Driver driver = new Driver(DRIVER_ID, "John Doe", "LICENSE123", RATING);

        when(driverRepository.findById(DRIVER_ID)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);

        driverService.rateDriver(DRIVER_ID, NEW_DRIVER_RATING);

        assertEquals(NEW_DRIVER_RATING, driver.getRating());
        verify(driverRepository).findById(DRIVER_ID);
        verify(driverRepository).save(driver);
    }

    @Test
    public void testDeleteDriver() {

        when(driverRepository.existsById(DRIVER_ID)).thenReturn(true);
        doNothing().when(driverRepository).deleteById(DRIVER_ID);


        driverService.deleteDriver(DRIVER_ID);


        verify(driverRepository).existsById(DRIVER_ID);
        verify(driverRepository).deleteById(DRIVER_ID);
    }

}
