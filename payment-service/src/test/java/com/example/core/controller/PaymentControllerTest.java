package com.example.core.controller;

import com.example.core.entity.DriverAssignmentRequest;
import com.example.core.entity.PaymentRequest;
import com.example.core.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPayment() {

        PaymentRequest paymentRequest = new PaymentRequest("CREDIT_CARD", 100, "token123", "passenger123");
        String expectedStatus = "Payment successful";

        when(paymentService.processPayment(paymentRequest)).thenReturn(expectedStatus);

        ResponseEntity<String> response = paymentController.processPayment(paymentRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatus, response.getBody());
        verify(paymentService, times(1)).processPayment(paymentRequest);
    }

    @Test
    public void testAssignDriverToPayment() {

        DriverAssignmentRequest driverAssignmentRequest = new DriverAssignmentRequest("paymentIntent123", "driver123");
        String expectedStatus = "Driver assigned successfully";


        when(paymentService.assignDriverToPayment(
                driverAssignmentRequest.getPaymentIntentId(),
                driverAssignmentRequest.getDriverId())).thenReturn(expectedStatus);


        ResponseEntity<String> response = paymentController.assignDriverToPayment(driverAssignmentRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatus, response.getBody());
        verify(paymentService, times(1)).assignDriverToPayment(
                driverAssignmentRequest.getPaymentIntentId(),
                driverAssignmentRequest.getDriverId());
    }
}

