package com.crew.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rides")
public class Ride {
    @Id
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

