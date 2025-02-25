package com.crew.core.repository;


import com.crew.core.entity.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findByPassengerId(String passengerId);

    List<Ride> findByDriverId(String driverId);

    Optional<Ride> findFirstByPassengerIdOrderByTimestampDesc(String passengerId);

}
