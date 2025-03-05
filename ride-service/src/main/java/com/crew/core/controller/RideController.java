package com.crew.core.controller;

import com.crew.core.dto.RideDto;
import com.crew.core.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
@Slf4j
@RestController
@RequestMapping("/api/rides")
@Tag(name = "Ride Management", description = "Operations related to rides logic")
public class RideController {

    @Autowired
    private RideService rideService;

    @Operation(summary = "Reserve Ride", description = "Reserves a new ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride reserved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/reserve")
    public ResponseEntity<RideDto> reserveRide(@RequestBody RideDto rideDto) {
        log.info("Request to reserve ride for passenger: {}, driver: {}", rideDto.getPassengerId(), rideDto.getDriverId());
        RideDto reservedRide = rideService.reserveRide(rideDto);
        return ResponseEntity.ok(reservedRide);
    }

    @Operation(summary = "Start Ride", description = "Starts a ride for the given driver and returns the ride details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride started successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/start/{driverId}")
    public ResponseEntity<RideDto> startRide(@PathVariable String driverId) {
        log.info("Request to start ride for passenger: {}", driverId);
        RideDto startedRide = rideService.startRide(Long.valueOf(driverId));
        return ResponseEntity.ok(startedRide);
    }

    @Operation(summary = "End Ride", description = "Ends a ride for the given driver and returns the ride details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride ended successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/end/{driverId}")
    public ResponseEntity<RideDto> endRide(@PathVariable String driverId) {
        log.info("Request to end ride for passenger: {}", driverId);
        RideDto endedRide = rideService.endRide(Long.valueOf(driverId));
        return ResponseEntity.ok(endedRide);
    }

    @Operation(summary = "Get Ride History", description = "Fetches the ride history for a given passenger or driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride history retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/history")
    public ResponseEntity<List<RideDto>> getRideHistory(@RequestParam(required = false) String passengerId,
                                                        @RequestParam(required = false) String driverId) {
        List<RideDto> rideHistory;
        if (passengerId != null) {
            log.info("Request to get ride history for passenger: {}", passengerId);
            rideHistory = rideService.getRideHistoryByPassenger(Long.valueOf(passengerId));
        } else if (driverId != null) {
            log.info("Request to get ride history for driver: {}", driverId);
            rideHistory = rideService.getRideHistoryByDriver(Long.valueOf(driverId));
        } else {
            log.warn("Both passengerId and driverId are null");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(rideHistory);
    }

    @Operation(summary = "Apply PromoCode", description = "Applies a promoCode for the given passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promo code applied successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid promo code or passenger ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/applyPromoCode")
    public ResponseEntity<Void> applyPromoCode(@RequestParam String passengerId, @RequestParam String promoCode) {
        log.info("Applying promo code for passenger: {}", passengerId);
        rideService.applyPromoCode(passengerId, promoCode);
        return ResponseEntity.ok().build();
    }
}

