package com.example.core.controller;




import com.example.core.entity.Driver;
import com.example.core.repository.DriverRepository;
import com.example.core.service.DriverService;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DriverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverService driverService;

    @Test
    public void testRegisterDriver() throws Exception {
        String driverJson = "{\"name\":\"Test Name\", \"license_number\":\"12345\", \"rating\":5}";

        mockMvc.perform(post("/drivers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(driverJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.license_number").value("12345"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void testEditProfile() throws Exception {
        Driver driver = new Driver();
        driver.setName("Old Name");
        driver.setLicenseNumber("12345");
        driver.setRating(4);
        Driver savedDriver = driverRepository.save(driver);

        String updatedDriverJson = "{\"name\":\"Updated Name\", \"license_number\":\"12345\", \"rating\":5}";

        mockMvc.perform(put("/drivers/edit/{id}", savedDriver.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDriverJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.license_number").value("12345"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void testGetDriverProfile() throws Exception {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);
        driverRepository.save(driver);

        mockMvc.perform(get("/drivers/profile/{id}", driver.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.license_number").value("12345"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void testRateDriver() throws Exception {
        Driver driver = new Driver();
        driver.setName("Test Name");
        driver.setLicenseNumber("12345");
        driver.setRating(4);
        driverRepository.save(driver);

        mockMvc.perform(post("/drivers/rate/{id}", driver.getId())
                        .param("rating", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDriver() throws Exception {
        Driver driver = new Driver();
        driver.setName("Delete Name");
        driver.setLicenseNumber("12345");
        driver.setRating(5);
        driverRepository.save(driver);

        mockMvc.perform(delete("/drivers/delete/{id}", driver.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
