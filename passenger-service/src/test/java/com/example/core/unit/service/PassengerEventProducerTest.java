package com.example.core.unit.service;

import com.example.core.service.PassengerEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PassengerEventProducerTest {

    @InjectMocks
    private PassengerEventProducer passengerEventProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPassengerEvent() {
        String passengerId = "123";

        passengerEventProducer.sendPassengerEvent(passengerId);
        verify(kafkaTemplate, times(1)).send("passenger-events", passengerId);
    }

    @Test
    public void testSendPassengerEvent_Exception() {
        String passengerId = "123";
        doThrow(new RuntimeException("Kafka send failed"))
                .when(kafkaTemplate).send("passenger-events", passengerId);

        assertThrows(RuntimeException.class, () -> passengerEventProducer.sendPassengerEvent(passengerId));
        verify(kafkaTemplate, times(1)).send("passenger-events", passengerId);
    }
}
