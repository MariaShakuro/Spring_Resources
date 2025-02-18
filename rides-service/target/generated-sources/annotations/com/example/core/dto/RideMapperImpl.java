package com.example.core.dto;

import com.example.core.entity.Ride;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-18T23:24:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class RideMapperImpl implements RideMapper {

    @Override
    public RideDto toDto(Ride ride) {
        if ( ride == null ) {
            return null;
        }

        RideDto rideDto = new RideDto();

        rideDto.setId( ride.getId() );
        rideDto.setStartLocation( ride.getStartLocation() );
        rideDto.setEndLocation( ride.getEndLocation() );
        rideDto.setFare( ride.getFare() );
        rideDto.setPaymentMethod( ride.getPaymentMethod() );
        rideDto.setPromoCode( ride.getPromoCode() );

        return rideDto;
    }

    @Override
    public Ride toEntity(RideDto rideDto) {
        if ( rideDto == null ) {
            return null;
        }

        Ride ride = new Ride();

        ride.setId( rideDto.getId() );
        ride.setStartLocation( rideDto.getStartLocation() );
        ride.setEndLocation( rideDto.getEndLocation() );
        ride.setFare( rideDto.getFare() );
        ride.setPaymentMethod( rideDto.getPaymentMethod() );
        ride.setPromoCode( rideDto.getPromoCode() );

        return ride;
    }
}
