package com.crew.core.unit.service;


import com.crew.core.service.PassengerEventListener;
import com.crew.core.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PassengerEventListenerTest {

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
        String passengerId = "passenger123";

        passengerEventListener.listenPassengerEvents(passengerId);
        verify(rideService, times(1)).setCurrentPassengerId(passengerId);
    }
}
