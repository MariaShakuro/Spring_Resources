package com.example.core.contract;

import com.example.core.repository.PassengerRepository;
import com.example.core.service.PassengerService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
class MockConfig {
    @Bean
    public PassengerRepository driverRepository() {
        return Mockito.mock(PassengerRepository.class);
    }
}
