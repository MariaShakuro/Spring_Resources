package com.example.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DriverEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendDriverEvent(String driverId) {
        kafkaTemplate.send("driver-events", driverId);
    }
}


