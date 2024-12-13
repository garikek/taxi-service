package com.software.modsen.rideservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.rideservice.config.DatabaseContainerConfiguration;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.repository.RideRepository;
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

import static com.software.modsen.rideservice.util.RideTestUtil.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(DatabaseContainerConfiguration.class)
public class RideControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRides() throws Exception {
        rideRepository.saveAll(List.of(getDefaultRide(), getUpdatedRide()));

        mockMvc.perform(get("/api/v1/rides"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rideDtoList", hasSize(2)))
                .andExpect(jsonPath("$.rideDtoList[0].pickupAddress", is(DEFAULT_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.rideDtoList[1].pickupAddress", is(UPDATED_PICKUP_ADDRESS)));
    }

    @Test
    void testAddRide() throws Exception {
        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultRideRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.pickupAddress", is(DEFAULT_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.destinationAddress", is(DEFAULT_DESTINATION_ADDRESS)));
    }

    @Test
    void testAddRide_InvalidPickupAddress() throws Exception {
        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultRideRequestWithInvalidPickupAddress())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddRide_InvalidDestinationAddress() throws Exception {
        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultRideRequestWithInvalidDestinationAddress())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetRideById_Success() throws Exception {
        Ride ride = rideRepository.save(getDefaultRide());

        mockMvc.perform(get("/api/v1/rides/{id}", ride.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupAddress", is(DEFAULT_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.destinationAddress", is(DEFAULT_DESTINATION_ADDRESS)));
    }

    @Test
    void testGetRideById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/rides/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateRide() throws Exception {
        Ride ride = rideRepository.save(getDefaultRide());

        mockMvc.perform(put("/api/v1/rides/{id}", ride.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedRideRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupAddress", is(UPDATED_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.destinationAddress", is(UPDATED_DESTINATION_ADDRESS)))
                .andExpect(jsonPath("$.price", is(UPDATED_PRICE)))
                .andExpect(jsonPath("$.status", is(UPDATED_STATUS.name())));
    }

    @Test
    void testUpdateRide_NotFound() throws Exception {
        mockMvc.perform(put("/api/v1/rides/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedRideRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRide() throws Exception {
        Ride ride = rideRepository.save(getDefaultRide());

        mockMvc.perform(delete("/api/v1/rides/{id}", ride.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRide_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/rides/999"))
                .andExpect(status().isNotFound());
    }
}
