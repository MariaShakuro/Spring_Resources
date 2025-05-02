package com.crew.core.unit.service;

import com.crew.core.dto.RideDto;
import com.crew.core.dto.RideMapper;
import com.crew.core.entity.Ride;
import com.crew.core.repository.RideRepository;
import com.crew.core.service.RideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RideServiceUnitTest {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @InjectMocks
    private RideService rideService;

    @Test
    public void testReserveRide() {
        RideDto rideDto = new RideDto();
        Ride ride = new Ride();
        Ride savedRide = new Ride();
        RideDto savedRideDto = new RideDto();

        when(rideMapper.toEntity(rideDto)).thenReturn(ride);
        when(rideRepository.save(ride)).thenReturn(savedRide);
        when(rideMapper.toDto(savedRide)).thenReturn(savedRideDto);

        RideDto result = rideService.reserveRide(rideDto);

        assertEquals(savedRideDto, result);
        verify(rideRepository).save(ride);
    }

    @Test
    public void testStartRide() {
        Long passengerId = 1L;
        Ride ride = new Ride();
        RideDto rideDto = new RideDto();

        when(rideRepository.findFirstByPassengerIdOrderByTimestampDesc(passengerId)).thenReturn(Optional.of(ride));
        when(rideRepository.save(ride)).thenReturn(ride);
        when(rideMapper.toDto(ride)).thenReturn(rideDto);

        RideDto result = rideService.startRide(passengerId);

        assertEquals(rideDto, result);
        verify(rideRepository).findFirstByPassengerIdOrderByTimestampDesc(passengerId);
        verify(rideRepository).save(ride);
    }

    @Test
    public void testEndRide() {
        Long passengerId = 1L;
        Ride ride = new Ride();
        RideDto rideDto = new RideDto();

        when(rideRepository.findFirstByPassengerIdOrderByTimestampDesc(passengerId)).thenReturn(Optional.of(ride));
        when(rideRepository.save(ride)).thenReturn(ride);
        when(rideMapper.toDto(ride)).thenReturn(rideDto);

        RideDto result = rideService.endRide(passengerId);

        assertEquals(rideDto, result);
        verify(rideRepository).findFirstByPassengerIdOrderByTimestampDesc(passengerId);
        verify(rideRepository).save(ride);
    }

}

