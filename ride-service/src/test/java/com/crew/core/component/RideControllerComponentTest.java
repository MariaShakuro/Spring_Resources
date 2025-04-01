package com.crew.core.component;

import com.crew.core.dto.RideDto;
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
public class RideControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/rides";
    private RideDto testRide;

    @BeforeEach
    void setup() {
        testRide = new RideDto(null, "1", "2","Minsk","Moscow","COMPLETED", 100.0,1L,"PROMOCODE123");
    }

    @Test
    void shouldReserveRide() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRide)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldStartRide() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/start/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldEndRide() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/end/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRideHistoryForPassenger() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/history")
                        .queryParam("passengerId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRideHistoryForDriver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/history")
                        .queryParam("driverId", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldApplyPromoCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/applyPromoCode")
                        .queryParam("passengerId", "1")
                        .queryParam("promoCode", "DISCOUNT10"))
                .andExpect(status().isOk());
    }
}
