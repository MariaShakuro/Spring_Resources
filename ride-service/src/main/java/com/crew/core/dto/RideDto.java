package com.crew.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity of a ride")
public class RideDto {
    @Schema(description = "Ride id")
    private String id;
    @Schema(description = "Ride passenger id")
    private String passengerId;
    @Schema(description = "Ride driver id")
    private String driverId;
    @Schema(description = "Ride start location")
    private String startLocation;
    @Schema(description = "Ride end location")
    private String endLocation;
    @Schema(description = "Ride status")
    private String status;
    @Schema(description = "Ride fare")
    private double fare;
    @Schema(description = "Ride timestamp")
    private Long timestamp;
    @Schema(description = "Ride promocode")
    private String promocode;

}
