package com.software.modsen.rideservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.rideservice.controller.impl.RideController;
import com.software.modsen.rideservice.dto.response.RideListDto;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.service.RideService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.software.modsen.rideservice.util.RideTestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(RideController.class)
class RideControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RideService rideService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRides() throws Exception {
        when(rideService.getRides()).thenReturn(new RideListDto(List.of(getDefaultRideResponse(), getUpdatedRideResponse())));

        mockMvc.perform(get("/api/v1/rides"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rideDtoList", hasSize(2)))
                .andExpect(jsonPath("$.rideDtoList[0].pickupAddress", is(DEFAULT_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.rideDtoList[1].pickupAddress", is(UPDATED_PICKUP_ADDRESS)));
    }

    @Test
    void testAddRide() throws Exception {
        when(rideService.addRide(Mockito.any(RideRequest.class))).thenReturn(getDefaultRideResponse());

        mockMvc.perform(post("/api/v1/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultRideRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(DEFAULT_ID.intValue())))
                .andExpect(jsonPath("$.pickupAddress", is(DEFAULT_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.destinationAddress", is(DEFAULT_DESTINATION_ADDRESS)));
    }

    @Test
    void testGetRideById() throws Exception {
        when(rideService.getRideById(DEFAULT_ID)).thenReturn(getDefaultRideResponse());

        mockMvc.perform(get("/api/v1/rides/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupAddress", is(DEFAULT_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.destinationAddress", is(DEFAULT_DESTINATION_ADDRESS)));
    }

    @Test
    void testDeleteRide() throws Exception {
        doNothing().when(rideService).deleteRide(DEFAULT_ID);

        mockMvc.perform(delete("/api/v1/rides/{id}", DEFAULT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateRide() throws Exception {
        when(rideService.updateRide(eq(DEFAULT_ID), any(RideRequest.class))).thenReturn(getUpdatedRideResponse());

        mockMvc.perform(put("/api/v1/rides/{id}", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedRideRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupAddress", is(UPDATED_PICKUP_ADDRESS)))
                .andExpect(jsonPath("$.destinationAddress", is(UPDATED_DESTINATION_ADDRESS)));
    }
}
