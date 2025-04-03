package com.example.core.unit.service;


import com.example.core.service.DriverEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DriverEventProducerUnitTest {

    @InjectMocks
    private DriverEventProducer driverEventProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setup() {
        driverEventProducer = new DriverEventProducer(kafkaTemplate);
    }
    private static final String DRIVER_ID_STRING="123";

    @Test
    public void testSendDriverEvent() {
        driverEventProducer.sendDriverEvent(DRIVER_ID_STRING);
        verify(kafkaTemplate, times(1)).send("driver-events",DRIVER_ID_STRING);
    }

    @Test
    public void testSendDriverEvent_Exception() {
        doThrow(new RuntimeException("Kafka send failed"))
                .when(kafkaTemplate).send("driver-events",DRIVER_ID_STRING);
        assertThrows(RuntimeException.class, () -> driverEventProducer.sendDriverEvent(DRIVER_ID_STRING));
        verify(kafkaTemplate, times(1)).send("driver-events",DRIVER_ID_STRING);
    }

}
