package com.crew.core.controller;

import com.crew.core.dto.RideDto;
import com.crew.core.service.RideService;
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
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/reserve")
    public ResponseEntity<RideDto> reserveRide(@RequestBody RideDto rideDto) {
        log.info("Request to reserve ride for passenger: {}, driver: {}", rideDto.getPassengerId(), rideDto.getDriverId());
        if (rideDto.getPassengerId() != null) rideDto = rideService.applyPromocodeDiscount(rideDto, Long.valueOf(rideDto.getPassengerId()));

        RideDto reservedRide = rideService.reserveRide(rideDto);
        return ResponseEntity.ok(reservedRide);
    }

    @PutMapping("/start/{driverId}")
    public ResponseEntity<RideDto> startRide(@PathVariable String driverId) {
        log.info("Request to start ride for passenger: {}", driverId);
        RideDto startedRide = rideService.startRide(Long.valueOf(driverId));
        return ResponseEntity.ok(startedRide);
    }

    @PutMapping("/end/{driverId}")
    public ResponseEntity<RideDto> endRide(@PathVariable String driverId) {
        log.info("Request to end ride for passenger: {}", driverId);
        RideDto endedRide = rideService.endRide(Long.valueOf(driverId));
        return ResponseEntity.ok(endedRide);
    }

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


}

