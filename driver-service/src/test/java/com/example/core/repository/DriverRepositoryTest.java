package com.example.core.repository;




import com.example.core.entity.Driver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Test
    public void testSaveDriver() {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);

        Driver savedDriver = driverRepository.save(driver);
        Optional<Driver> foundDriver = driverRepository.findById(savedDriver.getId());

        assertTrue(foundDriver.isPresent());
        assertEquals("Test Name", foundDriver.get().getName());
        assertEquals("12345", foundDriver.get().getLicenseNumber());
        assertEquals(5, foundDriver.get().getRating());
    }

    @Test
    public void testFindDriverById() {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);
        Driver savedDriver = driverRepository.save(driver);

        Optional<Driver> foundDriver = driverRepository.findById(savedDriver.getId());

        assertTrue(foundDriver.isPresent());
        assertEquals(savedDriver.getId(), foundDriver.get().getId());
    }

    @Test
    public void testDeleteDriver() {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);
        Driver savedDriver = driverRepository.save(driver);

        driverRepository.deleteById(savedDriver.getId());
        Optional<Driver> foundDriver = driverRepository.findById(savedDriver.getId());

        assertFalse(foundDriver.isPresent());
    }
}
