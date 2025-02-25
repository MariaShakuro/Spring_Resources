package com.example.core.controller;

import com.example.core.entity.Promocode;
import com.example.core.service.PromocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promocode")
public class PromocodeController {

    @Autowired
    private PromocodeService promocodeService;

    @PostMapping
    public ResponseEntity<Promocode> createPromocode(@RequestBody Promocode promocode) {
        return ResponseEntity.ok(promocodeService.createPromocode(promocode));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Promocode> getPromocode(@PathVariable String code) {
        return ResponseEntity.ok(promocodeService.getPromocode(code));
    }

    @PutMapping("/{code}/use")
    public ResponseEntity<Promocode> usePromocode(@PathVariable String code) {
        return ResponseEntity.ok(promocodeService.usePromocode(code));
    }
}
