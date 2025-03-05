package com.example.core.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverAssignmentRequest {
    private String paymentIntentId;
    private String driverId;
}

