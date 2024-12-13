package com.software.modsen.driverservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.driverservice.config.DatabaseContainerConfiguration;
import com.software.modsen.driverservice.model.Driver;
import com.software.modsen.driverservice.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.software.modsen.driverservice.util.DriverTestUtil.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(DatabaseContainerConfiguration.class)
public class DriverControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDrivers() throws Exception {
        driverRepository.saveAll(List.of(getUpdatedDriver(), getDefaultDriver()));

        mockMvc.perform(get("/api/v1/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverDtoList", hasSize(2)))
                .andExpect(jsonPath("$.driverDtoList[0].name", is(UPDATED_NAME)))
                .andExpect(jsonPath("$.driverDtoList[1].name", is(DEFAULT_NAME)));
    }

    @Test
    void testAddDriver() throws Exception {
        mockMvc.perform(post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)));
    }

    @Test
    void testAddDriver_InvalidEmail() throws Exception {
        mockMvc.perform(post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRequestWithInvalidEmail())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddDriver_InvalidPhoneNumber() throws Exception {
        mockMvc.perform(post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRequestWithInvalidPhoneNumber())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddDriver_InvalidVehicleNumber() throws Exception {
        mockMvc.perform(post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRequestWithInvalidVehicleNumber())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetDriverById_Success() throws Exception {
        Driver driver = driverRepository.save(getDefaultDriver());

        mockMvc.perform(get("/api/v1/drivers/{id}", driver.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)));
    }

    @Test
    void testGetDriverById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/drivers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateDriver() throws Exception {
        Driver driver = driverRepository.save(getDefaultDriver());

        mockMvc.perform(put("/api/v1/drivers/{id}", driver.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedDriverRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(UPDATED_NAME)))
                .andExpect(jsonPath("$.email", is(UPDATED_EMAIL)))
                .andExpect(jsonPath("$.vehicleNumber", is(UPDATED_VEHICLE_NUMBER)));
    }

    @Test
    void testUpdateDriver_NotFound() throws Exception {
        mockMvc.perform(put("/api/v1/drivers/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedDriverRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDriver() throws Exception {
        Driver driver = driverRepository.save(getDefaultDriver());

        mockMvc.perform(delete("/api/v1/drivers/{id}", driver.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteDriver_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/drivers/999"))
                .andExpect(status().isNotFound());
    }
}
