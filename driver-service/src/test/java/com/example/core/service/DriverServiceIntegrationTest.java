package com.example.core.service;


import com.example.core.dto.DriverDto;
import com.example.core.dto.DriverMapper;
import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
/*
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class DriverServiceIntegrationTest {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverMapper driverMapper;

    @Test
    public void testRegister() {
        DriverDto driverDto = new DriverDto();
        driverDto.setName("Test Name");
        driverDto.setLicenseNumber("12345");
        driverDto.setRating(5);

        DriverDto savedDriverDto = driverService.register(driverDto);

        Optional<Driver> foundDriver = driverRepository.findById(savedDriverDto.getId());
        assertTrue(foundDriver.isPresent());
        assertEquals("Test Name", foundDriver.get().getName());
        assertEquals("12345", foundDriver.get().getLicenseNumber());
        assertEquals(5, foundDriver.get().getRating());
    }

    @Test
    public void testEditProfile() {
        Driver driver = new Driver();
        driver.setName("Old Name");
        driver.setLicenseNumber("12345");
        driver.setRating(4);
        driverRepository.save(driver);

        DriverDto driverDto = new DriverDto();
        driverDto.setName("Updated Name");
        driverDto.setLicenseNumber("12345");
        driverDto.setRating(5);

        DriverDto updatedDriverDto = driverService.editProfile(driver.getId(), driverDto);

        Optional<Driver> foundDriver = driverRepository.findById(updatedDriverDto.getId());
        assertTrue(foundDriver.isPresent());
        assertEquals("Updated Name", foundDriver.get().getName());
        assertEquals("12345", foundDriver.get().getLicenseNumber());
        assertEquals(5, foundDriver.get().getRating());
    }

    @Test
    public void testGetDriverProfile() {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);
        driverRepository.save(driver);

        DriverDto driverDto = driverService.getDriverProfile(driver.getId());

        assertEquals("Test Name", driverDto.getName());
        assertEquals("12345", driverDto.getLicenseNumber());
        assertEquals(5, driverDto.getRating());
    }

    @Test
    public void testRateDriver() {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(4);
        driverRepository.save(driver);

        driverService.rateDriver(driver.getId(), 5);

        Optional<Driver> foundDriver = driverRepository.findById(driver.getId());
        assertTrue(foundDriver.isPresent());
        assertEquals(5, foundDriver.get().getRating());
    }

    @Test
    public void testDeleteDriver() {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);
        driverRepository.save(driver);

        driverService.deleteDriver(driver.getId());

        Optional<Driver> foundDriver = driverRepository.findById(driver.getId());
        assertTrue(foundDriver.isEmpty());
    }
}

*/