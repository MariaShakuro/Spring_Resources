package com.crew.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverEventListener {
    private final RideService rideService;

    @KafkaListener(topics = "driver-events", groupId = "ride-group")
    public void listenDriverEvents(String driverId) {
        rideService.setCurrentDriverId(driverId);
    }
}

