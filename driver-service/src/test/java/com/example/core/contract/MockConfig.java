package com.example.core.contract;

import com.example.core.repository.DriverRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;


@TestConfiguration
@ActiveProfiles("test")
class MockConfig {
  /*  @Bean
    public DriverRepository driverRepository() {
        return Mockito.mock(DriverRepository.class);
    }*/
}
