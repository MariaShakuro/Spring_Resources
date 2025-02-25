package com.example.core.repository;

import com.example.core.entity.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
    Promocode findByCode(String code);
}
