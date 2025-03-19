package com.example.gateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity<String> handleFallback() {
        return ResponseEntity.status(503).body("Сервис временно недоступен. Повторите попытку позже.");
    }
}
