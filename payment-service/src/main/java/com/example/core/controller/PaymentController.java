package com.example.core.controller;

import com.example.core.entity.DriverAssignmentRequest;
import com.example.core.entity.PaymentRequest;
import com.example.core.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        String paymentStatus = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(paymentStatus);
    }

    @PostMapping("/assign-driver")
    public ResponseEntity<String> assignDriverToPayment(@RequestBody DriverAssignmentRequest driverAssignmentRequest) {
        String assignmentStatus = paymentService.assignDriverToPayment(
                driverAssignmentRequest.getPaymentIntentId(),
                driverAssignmentRequest.getDriverId()
        );
        return ResponseEntity.ok(assignmentStatus);
    }
}


