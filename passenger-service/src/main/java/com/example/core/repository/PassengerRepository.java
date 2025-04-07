package com.example.core.repository;

import com.example.core.dto.PassengerDto;
import com.example.core.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

   // Optional<Passenger> findByEmail(String email);
  //  void deleteByEmail(String email);
}
