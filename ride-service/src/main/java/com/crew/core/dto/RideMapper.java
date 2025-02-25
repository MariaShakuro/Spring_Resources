package com.crew.core.dto;

import com.crew.core.entity.Ride;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {
    RideDto toDto(Ride ride);

    Ride toEntity(RideDto rideDto);

    List<RideDto> toDtoList(List<Ride> rides);
}
