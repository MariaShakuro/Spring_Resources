package com.example.core.controller;

import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);
    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerDto> registerPassenger(@RequestBody PassengerDto passengerDto) {

            log.info("Received passenger DTO: {}", passengerDto);
            PassengerDto registeredPassenger = passengerService.registerPassenger(passengerDto);
            return ResponseEntity.ok(registeredPassenger);

    }

    @GetMapping("/{email}")
    public ResponseEntity<Passenger> getPassengerByEmail(@PathVariable String email) {
        Optional<Passenger> passenger = passengerService.findPassengerByEmail(email);
        return passenger.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Passenger> updatePassenger(@RequestBody Passenger passenger) {
        Passenger updatedPassenger = passengerService.updatePassenger(passenger);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
