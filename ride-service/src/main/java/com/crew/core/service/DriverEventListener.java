package com.crew.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class DriverEventListener {
    private final RideService rideService;

    @KafkaListener(topics = "driver-events", groupId = "ride-group")
    public void listenDriverEvents(Long driverId) {
        log.info("Setting current driver ID: {}", driverId);
        rideService.setCurrentDriverId(driverId);
    }
}

