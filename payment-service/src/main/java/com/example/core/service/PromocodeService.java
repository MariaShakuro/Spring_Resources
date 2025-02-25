package com.example.core.service;

import com.example.core.entity.Promocode;
import com.example.core.repository.PromocodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromocodeService {

    @Autowired
    private PromocodeRepository promocodeRepository;

    public Promocode createPromocode(Promocode promocode) {
        return promocodeRepository.save(promocode);
    }

    public Promocode getPromocode(String code) {
        return promocodeRepository.findByCode(code);
    }

    public Promocode usePromocode(String code) {
        Promocode promocode = promocodeRepository.findByCode(code);
        if (promocode != null && !promocode.isUsed()) {
            promocode.setUsed(true);
            return promocodeRepository.save(promocode);
        }
        return null;
    }
}
