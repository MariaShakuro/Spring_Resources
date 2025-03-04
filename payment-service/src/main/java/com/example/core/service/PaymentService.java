package com.example.core.service;

import com.example.core.entity.PaymentRequest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class PaymentService {

    private Map<String, String> paymentRecords = new HashMap<>();

    public String processPayment(PaymentRequest paymentRequest) {
        String paymentType = paymentRequest.getPaymentType();
        int amount = paymentRequest.getAmount();
        String token = paymentRequest.getToken();
        String passengerId = paymentRequest.getPassengerId();

        if ("cash".equals(paymentType)) {
            return processCashPayment(amount, passengerId);
        } else if ("card".equals(paymentType)) {
            return processCardPayment(amount, token, passengerId);
        } else {
            return "Invalid payment type.";
        }
    }

    private String processCashPayment(int amount, String passengerId) {
        String paymentIntentId = "fake_cash_payment_intent_id_" + passengerId;
        paymentRecords.put(paymentIntentId, "Passenger ID: " + passengerId + ", Amount: " + amount);
        return "Cash payment processed for amount: " + amount;
    }

    private String processCardPayment(int amount, String token, String passengerId) {
        if ("test_valid_token".equals(token)) {
            String paymentIntentId = "fake_payment_intent_id_" + passengerId;
            paymentRecords.put(paymentIntentId, "Passenger ID: " + passengerId + ", Amount: " + amount);
            return "Card payment processed: " + paymentIntentId;
        } else if ("test_declined_token".equals(token)) {
            return "Card payment failed: Payment was declined.";
        } else {
            return "Card payment failed: Invalid token.";
        }
    }

    public String assignDriverToPayment(String paymentIntentId, String driverId) {
        String record = paymentRecords.get(paymentIntentId);
        if (record != null) {
            paymentRecords.put(paymentIntentId, record + ", Driver ID: " + driverId);
            return "Driver ID " + driverId + " assigned to Payment Intent ID: " + paymentIntentId;
        } else {
            return "Invalid Payment Intent ID.";
        }
    }
}


