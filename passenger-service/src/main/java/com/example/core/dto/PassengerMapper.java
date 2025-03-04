package com.example.core.dto;


import com.example.core.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDto toDto(Passenger passenger);

    Passenger toEntity(PassengerDto passengerDto);
    @Mapping(target = "id", ignore = true)
    void updatePassengerFromDto(PassengerDto passengerDto, @MappingTarget Passenger passenger);
}

