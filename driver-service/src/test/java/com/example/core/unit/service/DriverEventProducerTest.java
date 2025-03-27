package com.example.core.unit.service;


import com.example.core.service.DriverEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class DriverEventProducerTest {

    private DriverEventProducer driverEventProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        driverEventProducer = new DriverEventProducer(kafkaTemplate);
    }

    @Test
    public void testSendDriverEvent() {
        String driverId = "123";


        driverEventProducer.sendDriverEvent(driverId);


        verify(kafkaTemplate, times(1)).send("driver-events", driverId);
    }

    @Test
    public void testSendDriverEvent_Exception() {

        String driverId = "123";
        doThrow(new RuntimeException("Kafka send failed"))
                .when(kafkaTemplate).send("driver-events", driverId);

        assertThrows(RuntimeException.class, () -> driverEventProducer.sendDriverEvent(driverId));
        verify(kafkaTemplate, times(1)).send("driver-events", driverId);
    }

}
