package com.example.core.component;

import com.example.core.dto.DriverDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DriverControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/drivers";
    private DriverDto testDriver;

    @BeforeEach
    void setup() {
        testDriver = new DriverDto(null, "Alice", "1234567890", 5);
    }

    @Test
    void shouldRegisterDriverAndSendEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register-and-send-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDriver)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetDriverProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/profile/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldEditDriverProfile() throws Exception {
        testDriver.setName("Alice Updated");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDriver)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRateDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/rate/1")
                        .queryParam("rating", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/delete/1"))
                .andExpect(status().isNoContent());
    }
}
