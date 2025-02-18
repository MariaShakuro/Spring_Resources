package com.example.core.controller;

import com.example.core.dto.DriverDto;
import com.example.core.entity.Driver;
import com.example.core.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<DriverDto> registerDriver(@RequestBody DriverDto driverDto) {
        DriverDto savedDriver = driverService.register(driverDto);
        return ResponseEntity.ok(savedDriver);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProfile(@PathVariable Long id,@RequestBody DriverDto driverDto) {
        DriverDto updatedDriver = driverService.editProfile(id, driverDto);
        return ResponseEntity.ok(updatedDriver);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<DriverDto> getDriverProfile(@PathVariable Long id) {
        DriverDto driverDto = driverService.getDriverProfile(id);
        return ResponseEntity.ok(driverDto);
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<Void> rateDriver(@PathVariable Long id, @RequestParam int rating) {
        driverService.rateDriver(id, rating);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
    
}
