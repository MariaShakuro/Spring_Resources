package com.example.core.controller;


import com.example.core.entity.Passenger;
import com.example.core.repository.PassengerRepository;
import com.example.core.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/*
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PassengerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerService passengerService;

    @Test
    public void testRegisterPassenger() throws Exception {
        String passengerJson = "{\"email\":\"test@example.com\", \"name\":\"Test Name\", \"password\":\"password\", \"phoneNumber\":\"1234567890\"}";

        mockMvc.perform(post("/api/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passengerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test Name"));
    }

    @Test
    public void testGetPassengerByEmail() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setEmail("test@example.com");
        passenger.setName("Test Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passengerRepository.save(passenger);

        mockMvc.perform(get("/api/passenger/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test Name"));
    }

    @Test
    public void testUpdatePassenger() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setEmail("testupdate@example.com");
        passenger.setName("Old Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passengerRepository.save(passenger);

        String updatedPassengerJson = "{\"email\":\"testupdate@example.com\", \"name\":\"Updated Name\", \"password\":\"password\", \"phoneNumber\":\"1234567890\"}";

        mockMvc.perform(put("/api/passenger/update/{id}", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPassengerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testupdate@example.com"))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    public void testDeletePassenger() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setEmail("testdelete@example.com");
        passenger.setName("Delete Name");
        passenger.setPassword("password");
        passenger.setPhoneNumber("1234567890");
        passengerRepository.save(passenger);

        mockMvc.perform(delete("/api/passenger/delete/{id}", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

*/