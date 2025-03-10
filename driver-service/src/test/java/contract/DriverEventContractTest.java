package contract;

import com.example.core.service.DriverEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SpringBootTest
@AutoConfigureMessageVerifier
public class DriverEventContractTest {

    private DriverEventProducer driverEventProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        driverEventProducer = new DriverEventProducer(kafkaTemplate);
    }

    @Test
    public void testSendDriverEvent() {
        String driverId = "123";
        driverEventProducer.sendDriverEvent(driverId);

        verify(kafkaTemplate, times(1)).send("driver-events", driverId);

        assertThat(driverId).isNotNull();
    }
}

