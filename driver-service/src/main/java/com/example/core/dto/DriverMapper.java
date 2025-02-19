package com.example.core.dto;


import com.example.core.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDto toDto(Driver driver);

    Driver toEntity(DriverDto driverDto);

    @Mapping(target = "id", ignore = true)
    void updateDriverFromDto(DriverDto driverDto, @MappingTarget Driver driver);
}
