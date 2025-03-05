package com.example.core.controller;

import com.example.core.entity.DriverAssignmentRequest;
import com.example.core.entity.PaymentRequest;
import com.example.core.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment Management", description = "Operations related to payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Process Payment", description = "Processes the payment for a given payment request and returns the status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        String paymentStatus = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(paymentStatus);
    }

    @Operation(summary = "Assign Driver To Payment", description = "Assigns a driver to a specific payment intent and returns the assignment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver assigned to payment successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid assignment request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/assign-driver")
    public ResponseEntity<String> assignDriverToPayment(@RequestBody DriverAssignmentRequest driverAssignmentRequest) {
        String assignmentStatus = paymentService.assignDriverToPayment(
                driverAssignmentRequest.getPaymentIntentId(),
                driverAssignmentRequest.getDriverId()
        );
        return ResponseEntity.ok(assignmentStatus);
    }
}


