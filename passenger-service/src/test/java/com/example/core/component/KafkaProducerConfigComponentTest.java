package com.example.core.component;

import com.example.core.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestContainersConfig.class)
public class KafkaProducerConfigComponentTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testKafkaTemplateNotNull() {
        assertNotNull(kafkaTemplate);
    }

    @Test
    public void testSendMessage() {
        kafkaTemplate.send("passenger-events", "Test message");
    }
}

