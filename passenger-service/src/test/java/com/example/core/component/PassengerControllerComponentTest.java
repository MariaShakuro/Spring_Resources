package com.example.core.component;


import com.example.core.dto.PassengerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PassengerControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BASE_URL = "/api/passenger";
    private static final PassengerDto TEST_PASSENGER = new PassengerDto(null, "John Doe", "john@example.com", "password", "1234567890", "PROMO123");
    @Test
    public void testRegisterPassenger() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/register-and-send-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_PASSENGER)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPassengerByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/john@example.com"))
                .andExpect(status().isOk());
    }
}


