package com.example.core.unit.service;

import com.example.core.service.PassengerEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class PassengerEventProducerUnitTest {

    @InjectMocks
    private PassengerEventProducer passengerEventProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String PASSENGER_ID_STRING="123";
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPassengerEvent() {

        passengerEventProducer.sendPassengerEvent(PASSENGER_ID_STRING);
        verify(kafkaTemplate, times(1)).send("passenger-events", PASSENGER_ID_STRING);
    }

    @Test
    public void testSendPassengerEvent_Exception() {
        doThrow(new RuntimeException("Kafka send failed"))
                .when(kafkaTemplate).send("passenger-events",PASSENGER_ID_STRING);

        assertThrows(RuntimeException.class, () -> passengerEventProducer.sendPassengerEvent(PASSENGER_ID_STRING));
        verify(kafkaTemplate, times(1)).send("passenger-events",PASSENGER_ID_STRING);
    }
}
