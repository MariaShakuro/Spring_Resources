package com.example.core.controller;

import com.example.core.dto.DriverDto;
import com.example.core.service.DriverEventProducer;
import com.example.core.service.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@Tag(name = "Driver management", description = "Operations related to driver")
public class DriverController {

    private static final Logger log = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverEventProducer driverEventProducer;

    @Operation(summary = "Register and send event", description = "Registers a new driver and sends an event")
    @PostMapping("/register-and-send-event")
    public ResponseEntity<DriverDto> registerAndSendDriverEvent(@RequestBody DriverDto driverDto) {
        log.info("Registering new driver:{}", driverDto);
        DriverDto savedDriver = driverService.register(driverDto);

        driverEventProducer.sendDriverEvent(savedDriver.getId().toString());
        return ResponseEntity.ok(savedDriver);
    }

    @Operation(summary = "Edit Profile", description = "Edits the profile of an existing driver")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProfile(@PathVariable Long id, @RequestBody DriverDto driverDto) {
        log.info("Editing profile of driver with id:{}", id);
        DriverDto updatedDriver = driverService.editProfile(id, driverDto);
        return ResponseEntity.ok(updatedDriver);
    }

    @Operation(summary = "Get Driver Profile", description = "Fetches the profile of an existing driver")
    @GetMapping("/profile/{id}")
    public ResponseEntity<DriverDto> getDriverProfile(@PathVariable Long id) {
        log.info("Fetching profile of driver with id:{}", id);
        DriverDto driverDto = driverService.getDriverProfile(id);
        return ResponseEntity.ok(driverDto);
    }

    @Operation(summary = "Rate Driver", description = "Rates an existing driver")
    @PostMapping("/rate/{id}")
    public ResponseEntity<Void> rateDriver(@PathVariable Long id, @RequestParam int rating) {
        log.info("Rating driver with id:{} with rating:{}", id, rating);
        driverService.rateDriver(id, rating);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete Driver", description = "Deletes an existing driver")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        log.info("Deleting driver with:{}", id);
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }



}
