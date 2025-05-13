package com.crew.core.service;


import com.crew.core.dto.RideDto;
import com.crew.core.dto.RideMapper;
import com.crew.core.entity.Ride;
import com.crew.core.exception.ResourceNotFoundException;
import com.crew.core.repository.RideRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class RideService {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private RideMapper rideMapper;
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private Long currentPassengerId;
    private Long currentDriverId;
    private final Map<Long, String> passengerPromoCodes = new HashMap<>();

   public void setCurrentPassengerId(Long passengerId) {
        this.currentPassengerId = passengerId;
    }

    public void setCurrentDriverId(Long driverId) {
        this.currentDriverId = driverId;
    }

    @Transactional
    public RideDto reserveRide(RideDto rideDto) {
        log.info("Reserving ride for passenger: {},driver: {}", rideDto.getPassengerId(), rideDto.getDriverId());
        Ride ride = rideMapper.toEntity(rideDto);

        Optional<String> promoCodeOpt = Optional.ofNullable(passengerPromoCodes.get(rideDto.getPassengerId()));
        promoCodeOpt.ifPresent(promoCode -> {
            double discountedFare = ride.getFare() * 0.9;
            ride.setFare(discountedFare);
        });

        Ride savedRide = rideRepository.save(ride);
        return rideMapper.toDto(savedRide);
        /*ride.setPassengerId(currentPassengerId);
        ride.setDriverId(currentDriverId);

        String promoCode = passengerPromoCodes.get(currentPassengerId);
        if (promoCode != null) {
            double discountedFare = ride.getFare() * 0.9;
            ride.setFare(discountedFare);
        }

        Ride savedRide = rideRepository.save(ride);
        return rideMapper.toDto(savedRide);*/
    }

    @Transactional
    public RideDto startRide(Long passengerId) {
        log.info("Starting ride for passenger: {}", passengerId);
        Ride ride = rideRepository.findFirstByPassengerIdOrderByTimestampDesc(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        ride.setStatus(STATUS_IN_PROGRESS);
        Ride updatedRide = rideRepository.save(ride);
        return rideMapper.toDto(updatedRide);
    }

    @Transactional
    public RideDto endRide(Long passengerId) {
        log.info("Ending ride for passenger: {}", passengerId);
        Ride ride = rideRepository.findFirstByPassengerIdOrderByTimestampDesc(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        ride.setStatus(STATUS_COMPLETED);
        Ride updatedRide = rideRepository.save(ride);
        return rideMapper.toDto(updatedRide);
    }

    public List<RideDto> getRideHistoryByPassenger(Long passengerId) {
        log.info("Getting ride history for passenger: {}", passengerId);
        return rideMapper.toDtoList(rideRepository.findByPassengerId(passengerId));
    }
    public List<RideDto> getRideHistoryByDriver(Long driverId) {
        log.info("Getting ride history for driver: {}", driverId);
       /* List<Ride> rides = rideRepository.findByDriverId(String.valueOf(driverId));
        return rideMapper.toDtoList(rides);*/
        return rideMapper.toDtoList(rideRepository.findByDriverId(driverId));
    }
    public void applyPromoCode(Long passengerId, String promoCode) {
        passengerPromoCodes.put(passengerId, promoCode);
    }
}
