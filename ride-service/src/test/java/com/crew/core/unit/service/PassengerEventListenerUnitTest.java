package com.crew.core.unit.service;


import com.crew.core.service.PassengerEventListener;
import com.crew.core.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
public class PassengerEventListenerUnitTest {

    @Mock
    private RideService rideService;

    @InjectMocks
    private PassengerEventListener passengerEventListener;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListenPassengerEvents() {
        Long passengerId =  123L;

        passengerEventListener.setPassengerId(passengerId);
        verify(rideService, times(1)).setCurrentPassengerId(passengerId);
    }
}
