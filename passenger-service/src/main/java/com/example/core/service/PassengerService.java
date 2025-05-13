package com.example.core.service;

import com.example.core.dto.PassengerDto;
import com.example.core.dto.PassengerMapper;
import com.example.core.entity.Passenger;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.PassengerRepository;
import io.micrometer.tracing.annotation.NewSpan;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PassengerService {
    private static final Logger log = LoggerFactory.getLogger(PassengerService.class);

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Transactional
    @NewSpan("register-passenger")
    public PassengerDto registerPassenger(PassengerDto passengerDto) {
        log.info("Registering passenger: {}", passengerDto);
        Passenger passenger = passengerMapper.toEntity(passengerDto);
        log.info("Mapped entity: {}", passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info("Saved passenger: {}", savedPassenger);
        return passengerMapper.toDto(savedPassenger);
    }

   /* public Optional<PassengerDto> findPassengerByEmail(String email) {
        Optional<Passenger> passenger = passengerRepository.findByEmail(email);
        return passenger.map(passengerMapper::toDto);
    }*/

    @Transactional
    public PassengerDto updatePassenger(PassengerDto passengerDto) {
        Passenger passenger = passengerRepository.findById(passengerDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        passengerMapper.updatePassengerFromDto(passengerDto, passenger);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(updatedPassenger);
    }

    @Transactional
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            log.error("Attempted to delete non-existing passenger with ID {}", id);
            throw new ResourceNotFoundException("Passenger not found");
        }
        passengerRepository.deleteById(id);
    }


}
