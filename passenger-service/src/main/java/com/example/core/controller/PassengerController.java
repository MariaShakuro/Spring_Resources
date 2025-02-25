package com.example.core.controller;

import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.service.PassengerEventProducer;
import com.example.core.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private PassengerEventProducer passengerEventProducer;

    @PostMapping("/register-and-send-event")
    public ResponseEntity<PassengerDto> registerAndSendPassengerEvent(@RequestBody PassengerDto passengerDto) {

        log.info("Received passenger DTO: {}", passengerDto);
        PassengerDto registeredPassenger = passengerService.registerPassenger(passengerDto);
        passengerService.registerPassenger(passengerDto);

        passengerEventProducer.sendPassengerEvent(registeredPassenger.getId().toString());
        return ResponseEntity.ok(registeredPassenger);

    }

    @GetMapping("/{email}")
    public ResponseEntity<Passenger> getPassengerByEmail(@PathVariable String email) {
        log.info("Received request to get passenger by email: {}", email);
        Optional<Passenger> passenger = passengerService.findPassengerByEmail(email);
        return passenger.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(@PathVariable Long id, @RequestBody PassengerDto passengerDto) {
        passengerDto.setId(id);
        log.info("Received request to update passenger: {}", passengerDto);
        PassengerDto updatedPassenger = passengerService.updatePassenger(passengerDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        log.info("Received request to delete passenger with id: {}", id);
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/apply-promocode/{passengerId}")
    public ResponseEntity<String> applyPromocode(@PathVariable Long passengerId, @RequestParam String promocode) {
        log.info("Applying promocode {} for passenger {}", promocode, passengerId);
        passengerService.applyPromocode(passengerId, promocode);
        return ResponseEntity.ok("Promocode applied successfully.");
    }

    @GetMapping("/promocode/{passengerId}")
    public ResponseEntity<String> getPromocode(@PathVariable Long passengerId) {
        String promocode = passengerService.getPromocode(passengerId);
        return ResponseEntity.ok(promocode);
    }

}
