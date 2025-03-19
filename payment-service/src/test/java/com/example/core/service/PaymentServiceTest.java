package com.example.core.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.core.entity.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        paymentService = new PaymentService();
    }

    @Test
    public void testProcessPayment_CashPayment() {

        PaymentRequest paymentRequest = new PaymentRequest("cash", 100, null, "passenger123");
        String result = paymentService.processPayment(paymentRequest);
        assertEquals("Cash payment processed for amount: 100", result);
    }

    @Test
    public void testProcessPayment_CardPayment_ValidToken() {
        PaymentRequest paymentRequest = new PaymentRequest("card", 200, "test_valid_token", "passenger456");
        String result = paymentService.processPayment(paymentRequest);
        assertTrue(result.startsWith("Card payment processed: fake_payment_intent_id_"));
        assertTrue(result.contains("passenger456"));
    }

    @Test
    public void testProcessPayment_CardPayment_InvalidToken() {
        PaymentRequest paymentRequest = new PaymentRequest("card", 300, "invalid_token", "passenger789");
        String result = paymentService.processPayment(paymentRequest);
        assertEquals("Card payment failed: Invalid token.", result);
    }

    @Test
    public void testProcessPayment_CardPayment_DeclinedToken() {
        PaymentRequest paymentRequest = new PaymentRequest("card", 150, "test_declined_token", "passenger101");
        String result = paymentService.processPayment(paymentRequest);
        assertEquals("Card payment failed: Payment was declined.", result);
    }

    @Test
    public void testProcessPayment_InvalidPaymentType() {
        PaymentRequest paymentRequest = new PaymentRequest("unknown", 50, null, "passenger999");
        String result = paymentService.processPayment(paymentRequest);
        assertEquals("Invalid payment type.", result);
    }

    @Test
    public void testAssignDriverToPayment_ValidPaymentIntent() {
        String paymentIntentId = "fake_cash_payment_intent_id_passenger123";
        paymentService.processPayment(new PaymentRequest("cash", 100, null, "passenger123"));
        String result = paymentService.assignDriverToPayment(paymentIntentId, "driver123");
        assertEquals("Driver ID driver123 assigned to Payment Intent ID: fake_cash_payment_intent_id_passenger123", result);
    }

    @Test
    public void testAssignDriverToPayment_InvalidPaymentIntent() {
        String result = paymentService.assignDriverToPayment("invalid_intent", "driver999");
        assertEquals("Invalid Payment Intent ID.", result);
    }
}
