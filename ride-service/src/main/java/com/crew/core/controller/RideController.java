package com.crew.core.controller;

import com.crew.core.dto.RideDto;
import com.crew.core.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Slf4j
@RestController
@RequestMapping("/api/v1/rides")
@Tag(name = "Ride Management", description = "Operations related to rides logic")
public class RideController {

    @Autowired
    private RideService rideService;

    @Operation(summary = "Reserve Ride", description = "Reserves a new ride")
    @PostMapping("/reserve")
    public ResponseEntity<RideDto> reserveRide(@Valid @RequestBody RideDto rideDto) {
        log.info("Request to reserve ride for passenger: {}, driver: {}", rideDto.getPassengerId(), rideDto.getDriverId());
        RideDto reservedRide = rideService.reserveRide(rideDto);
        return ResponseEntity.ok(reservedRide);
    }

    @Operation(summary = "Start Ride", description = "Starts a ride for the given driver and returns the ride details")
    @PutMapping("/start/{driverId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long driverId) {
        log.info("Request to start ride for passenger: {}", driverId);
        RideDto startedRide = rideService.startRide(driverId);
        return ResponseEntity.ok(startedRide);
    }

    @Operation(summary = "End Ride", description = "Ends a ride for the given driver and returns the ride details")
    @PutMapping("/end/{driverId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long driverId) {
        log.info("Request to end ride for passenger: {}", driverId);
        RideDto endedRide = rideService.endRide(driverId);
        return ResponseEntity.ok(endedRide);
    }

    @Operation(summary = "Get Ride History", description = "Fetches the ride history for a given passenger or driver")
    @GetMapping("/history")
    public ResponseEntity<List<RideDto>> getRideHistory(@RequestParam(required = false) Long passengerId,
                                                        @RequestParam(required = false) Long driverId) {
       /* List<RideDto> rideHistory;
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
        return ResponseEntity.ok(rideHistory);*/
        Optional<Long> passengerOpt = Optional.ofNullable(passengerId).map(Long::valueOf);
        Optional<Long> driverOpt = Optional.ofNullable(driverId).map(Long::valueOf);

        if (passengerOpt.isPresent()) {
            log.info("Fetching ride history for passenger ID: {}", passengerOpt.get());
            return ResponseEntity.ok(rideService.getRideHistoryByPassenger(passengerOpt.get()));
        } else if (driverOpt.isPresent()) {
            log.info("Fetching ride history for driver ID: {}", driverOpt.get());
            return ResponseEntity.ok(rideService.getRideHistoryByDriver(driverOpt.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Apply PromoCode", description = "Applies a promoCode for the given passenger")
    @PostMapping("/applyPromoCode")
    public ResponseEntity<Void> applyPromoCode(@RequestParam Long passengerId, @RequestParam String promoCode) {
        log.info("Applying promo code for passenger: {}", passengerId);
        rideService.applyPromoCode(passengerId, promoCode);
        return ResponseEntity.ok().build();
    }
}

