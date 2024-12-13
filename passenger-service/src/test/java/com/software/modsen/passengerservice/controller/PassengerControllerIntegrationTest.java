package com.software.modsen.passengerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.passengerservice.config.DatabaseContainerConfiguration;
import com.software.modsen.passengerservice.model.Passenger;
import com.software.modsen.passengerservice.repository.PassengerRepository;
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

import static com.software.modsen.passengerservice.util.PassengerTestUtil.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(DatabaseContainerConfiguration.class)
public class PassengerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPassengers() throws Exception {
        passengerRepository.saveAll(List.of(getDefaultPassenger(), getUpdatedPassenger()));

        mockMvc.perform(get("/api/v1/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerDTOList", hasSize(2)))
                .andExpect(jsonPath("$.passengerDTOList[0].name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.passengerDTOList[1].name", is(UPDATED_NAME)));
    }

    @Test
    void testAddPassenger() throws Exception {
        mockMvc.perform(post("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)));
    }

    @Test
    void testAddPassenger_InvalidEmail() throws Exception {
        mockMvc.perform(post("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRequestWithInvalidEmail())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddPassenger_InvalidPhoneNumber() throws Exception {
        mockMvc.perform(post("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRequestWithInvalidPhoneNumber())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPassengerById_Success() throws Exception {
        Passenger passenger = passengerRepository.save(getDefaultPassenger());

        mockMvc.perform(get("/api/v1/passengers/{id}", passenger.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)));
    }

    @Test
    void testGetPassengerById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/passengers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePassenger() throws Exception {
        Passenger passenger = passengerRepository.save(getDefaultPassenger());

        mockMvc.perform(put("/api/v1/passengers/{id}", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedPassengerRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(UPDATED_NAME)));
    }

    @Test
    void testUpdatePassenger_NotFound() throws Exception {
        mockMvc.perform(put("/api/v1/passengers/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedPassengerRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePassenger() throws Exception {
        Passenger passenger = passengerRepository.save(getDefaultPassenger());

        mockMvc.perform(delete("/api/v1/passengers/{id}", passenger.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePassenger_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/passengers/999"))
                .andExpect(status().isNotFound());
    }
}
