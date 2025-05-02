package com.crew.core.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rides")
public class Ride {
    @Id
    private ObjectId id;
    private Long passengerId;
    private Long driverId;
    private String startLocation;
    private String endLocation;
    private String status;
    private double fare;
    private Long timestamp;
    private String promocode;

}

