package com.example.core.controller;

import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import com.example.core.service.PassengerEventProducer;
import com.example.core.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/passenger")
@Tag(name = "Passenger Management", description = "Operations related to passenger")
public class PassengerController {
    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerService passengerService;
    private final PassengerEventProducer passengerEventProducer;
    @Autowired
    public PassengerController(PassengerService passengerService, PassengerEventProducer passengerEventProducer) {
        this.passengerService = passengerService;
        this.passengerEventProducer = passengerEventProducer;
    }



        @Operation(summary = "Register and Send Passenger Event", description = "Registers a new passenger and sends an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register-and-send-event")
    public ResponseEntity<PassengerDto> registerAndSendPassengerEvent(@RequestBody PassengerDto passengerDto) {

        log.info("Received passenger DTO: {}", passengerDto);
        PassengerDto registeredPassenger = passengerService.registerPassenger(passengerDto);

        passengerEventProducer.sendPassengerEvent(registeredPassenger.getId().toString());


        return ResponseEntity.ok(registeredPassenger);

    }

    @Operation(summary = "Get Passenger By Email", description = "Gets an existing passenger by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{email}")
    public ResponseEntity<PassengerDto> getPassengerByEmail(@PathVariable String email) {
        log.info("Received request to get passenger by email: {}", email);

        Optional<PassengerDto> passengerDto = passengerService.findPassengerByEmail(email);

        if (passengerDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(passengerDto.get());
    }


    @Operation(summary = "Update Passenger", description = "Updates an existing passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger updated successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(@PathVariable Long id, @RequestBody PassengerDto passengerDto) {
        passengerDto.setId(id);
        log.info("Received request to update passenger: {}", passengerDto);
        PassengerDto updatedPassenger = passengerService.updatePassenger(passengerDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @Operation(summary = "Delete Passenger", description = "Deletes an existing passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Passenger deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        log.info("Received request to delete passenger with id: {}", id);
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }



}
