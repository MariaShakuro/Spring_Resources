package com.example.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPassengerEvent(String passengerId) {
        kafkaTemplate.send("passenger-events", passengerId);
    }
}

