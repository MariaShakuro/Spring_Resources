package com.example.core.service;

import com.example.core.dto.DriverDto;
import com.example.core.dto.DriverMapper;
import com.example.core.entity.Driver;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.DriverRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DriverMapper driverMapper;

    @Transactional
    public DriverDto register(DriverDto driverDto) {
        Driver driver = driverMapper.toEntity(driverDto);
        Driver savedDriver = driverRepository.save(driver);
        return driverMapper.toDto(savedDriver);
    }

    public DriverDto editProfile(Long id, DriverDto driverDto) {
        Driver existedDriver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        driverMapper.updateDriverFromDto(driverDto, existedDriver);
        Driver updatedDriver = driverRepository.save(existedDriver);
        return driverMapper.toDto(updatedDriver);
    }

    public DriverDto getDriverProfile(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        return driverMapper.toDto(driver);

    }

    public void rateDriver(Long id, int rating) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        driver.setRating(rating);
        driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        if (driverRepository.existsById(id)) driverRepository.deleteById(id);
        else throw new ResourceNotFoundException("Driver not found");

    }
}

