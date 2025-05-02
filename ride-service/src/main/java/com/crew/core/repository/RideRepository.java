package com.crew.core.repository;


import com.crew.core.entity.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findByPassengerId(Long passengerId);

    List<Ride> findByDriverId(Long driverId);

    Optional<Ride> findFirstByPassengerIdOrderByTimestampDesc(Long passengerId);

}
