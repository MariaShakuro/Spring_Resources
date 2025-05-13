package com.example.core.controller;

import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.service.PassengerEventProducer;
import com.example.core.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
@Tag(name = "Passenger Management", description = "Operations related to passenger")
public class PassengerController {
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);

    //@Autowired
    private final PassengerService passengerService;
    private final PassengerEventProducer passengerEventProducer;
   /* @Autowired
    public PassengerController(PassengerService passengerService, PassengerEventProducer passengerEventProducer) {
        this.passengerService = passengerService;
        this.passengerEventProducer = passengerEventProducer;
    }*/


    @Operation(summary = "Register and Send Passenger Event", description = "Registers a new passenger and sends an event")
    @PostMapping("/register-and-send-event")
    public ResponseEntity<PassengerDto> registerAndSendPassengerEvent(@Valid @RequestBody PassengerDto passengerDto) {

        log.info("Received passenger DTO: {}", passengerDto);
        PassengerDto registeredPassenger = passengerService.registerPassenger(passengerDto);
        passengerEventProducer.sendPassengerEvent(registeredPassenger.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredPassenger);

    }

   /* @Operation(summary = "Get Passenger By Email", description = "Gets an existing passenger by email")
    @GetMapping("/{email}")
    public ResponseEntity<PassengerDto> getPassengerByEmail(@PathVariable String email) {
        log.info("Received request to get passenger by email: {}", email);
        Optional<PassengerDto> passengerDto = passengerService.findPassengerByEmail(email);
        if (passengerDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passengerDto.get());
    }*/


    @Operation(summary = "Update Passenger", description = "Updates an existing passenger")
    @PutMapping("/update/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(@PathVariable("id") Long id, @Valid @RequestBody PassengerDto passengerDto) {
        passengerDto.setId(id);
        log.info("Received request to update passenger: {}", passengerDto);
        PassengerDto updatedPassenger = passengerService.updatePassenger(passengerDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @Operation(summary = "Delete Passenger", description = "Deletes an existing passenger")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") Long id) {
        log.info("Received request to delete passenger with id: {}", id);
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }


}
