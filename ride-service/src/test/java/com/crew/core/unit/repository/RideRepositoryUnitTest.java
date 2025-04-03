package com.crew.core.unit.repository;

import com.crew.core.entity.Ride;
import com.crew.core.repository.RideRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@ActiveProfiles("test")
public class RideRepositoryUnitTest {

    @Container
    public static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @AfterAll
    static void tearDown(){
        mongoDBContainer.stop();
        kafkaContainer.stop();
    }
    @Autowired
    private RideRepository rideRepository;



    @BeforeEach
    public void setUp() {
        rideRepository.deleteAll();
    }

    @Test
    public void testFindByPassengerId() {
        Ride ride = new Ride(null, "passenger123", "driver456", "Start Point", "End Point", "COMPLETED", 100.50, System.currentTimeMillis(), "PROMO10");
        rideRepository.save(ride);

        List<Ride> results = rideRepository.findByPassengerId("passenger123");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(ride.getPassengerId(), results.get(0).getPassengerId());
    }

    @Test
    public void testFindByDriverId() {
        Ride ride = new Ride(null, "passenger789", "driver123", "Start Point", "End Point", "IN_PROGRESS", 75.00, System.currentTimeMillis(), "PROMO15");
        rideRepository.save(ride);

        List<Ride> results = rideRepository.findByDriverId("driver123");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(ride.getDriverId(), results.get(0).getDriverId());
    }

    @Test
    public void testFindFirstByPassengerIdOrderByTimestampDesc() {
        Ride olderRide = new Ride(null, "passenger123", "driver123", "Start Point A", "End Point A", "COMPLETED", 50.00, System.currentTimeMillis() - 10000, "PROMO5");
        Ride newerRide = new Ride(null, "passenger123", "driver123", "Start Point B", "End Point B", "COMPLETED", 70.00, System.currentTimeMillis(), "PROMO10");

        rideRepository.save(olderRide);
        rideRepository.save(newerRide);

        Optional<Ride> result = rideRepository.findFirstByPassengerIdOrderByTimestampDesc("passenger123");

        assertTrue(result.isPresent());
        assertEquals(newerRide.getTimestamp(), result.get().getTimestamp());
    }

    @Test
    public void testFindByPassengerId_NotFound() {
        List<Ride> results = rideRepository.findByPassengerId("nonexistentPassenger");

        assertTrue(results.isEmpty());
    }
}
