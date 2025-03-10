package com.crew.core.service;

import com.crew.core.dto.RideDto;
import com.crew.core.dto.RideMapper;
import com.crew.core.entity.Ride;
import com.crew.core.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RideServiceTest {

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
        String passengerId = "1";
        Ride ride = new Ride();
        RideDto rideDto = new RideDto();

        when(rideRepository.findFirstByPassengerIdOrderByTimestampDesc(passengerId)).thenReturn(Optional.of(ride));
        when(rideRepository.save(ride)).thenReturn(ride);
        when(rideMapper.toDto(ride)).thenReturn(rideDto);

        RideDto result = rideService.startRide(Long.valueOf(passengerId));

        assertEquals(rideDto, result);
        verify(rideRepository).findFirstByPassengerIdOrderByTimestampDesc(passengerId);
        verify(rideRepository).save(ride);
    }

    @Test
    public void testEndRide() {
        String passengerId = "1";
        Ride ride = new Ride();
        RideDto rideDto = new RideDto();

        when(rideRepository.findFirstByPassengerIdOrderByTimestampDesc(passengerId)).thenReturn(Optional.of(ride));
        when(rideRepository.save(ride)).thenReturn(ride);
        when(rideMapper.toDto(ride)).thenReturn(rideDto);

        RideDto result = rideService.endRide(Long.valueOf(passengerId));

        assertEquals(rideDto, result);
        verify(rideRepository).findFirstByPassengerIdOrderByTimestampDesc(passengerId);
        verify(rideRepository).save(ride);
    }

 /*   @Test
    public void testGetRideHistory() {
        String passengerId = "1";
        List<Ride> rides = Arrays.asList(new Ride(), new Ride());
        List<RideDto> rideDtos = Arrays.asList(new RideDto(), new RideDto());

        when(rideRepository.findByPassengerId(passengerId)).thenReturn(rides);
        when(rideMapper.toDtoList(rides)).thenReturn(rideDtos);

        List<RideDto> result = rideService.getRideHistory(Long.valueOf(passengerId));

        assertEquals(rideDtos, result);
        verify(rideRepository).findByPassengerId(passengerId);
    }*/
}

