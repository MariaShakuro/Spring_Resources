package com.example.core.contract;

import com.example.core.repository.DriverRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
class MockConfig {
    @Bean
    public DriverRepository driverRepository() {
        return Mockito.mock(DriverRepository.class);
    }
}
