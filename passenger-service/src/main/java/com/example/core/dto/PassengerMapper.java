package com.example.core.dto;


import com.example.core.entity.Passenger;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDto toDto(Passenger passenger);

    Passenger toEntity(PassengerDto passengerDto);

}

