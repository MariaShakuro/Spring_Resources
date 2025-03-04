package com.crew.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerEventListener {
    private final RideService rideService;

    @KafkaListener(topics = "passenger-events", groupId = "ride-group")
    public void listenPassengerEvents(String passengerId) {
        rideService.setCurrentPassengerId(passengerId);
    }
}
