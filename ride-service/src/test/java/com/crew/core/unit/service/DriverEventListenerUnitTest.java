package com.crew.core.unit.service;


import com.crew.core.service.DriverEventListener;
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
public class DriverEventListenerUnitTest {

    @Mock
    private RideService rideService;

    @InjectMocks
    private DriverEventListener driverEventListener;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListenDriverEvents() {
        String driverId = "driver123";
        driverEventListener.listenDriverEvents(driverId);
        verify(rideService, times(1)).setCurrentDriverId(driverId);
    }
}
