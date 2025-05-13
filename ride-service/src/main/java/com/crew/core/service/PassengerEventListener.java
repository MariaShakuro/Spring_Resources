package com.crew.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerEventListener {
    private final RideService rideService;

    @KafkaListener(topics = "passenger-events", groupId = "ride-group")
    public void setPassengerId(Long passengerId) {
        log.info("Setting current passenger ID: {}", passengerId);
        rideService.setCurrentPassengerId(passengerId);
    }

}
