package com.crew.core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {
    private String id;
    private String passengerId;
    private String driverId;
    private String startLocation;
    private String endLocation;
    private String status;
    private double fare;
    private long timestamp;
    private String promocode;

}
