package com.example.core.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String paymentType;
    private int amount;
    private String token;
    private String passengerId;
}

