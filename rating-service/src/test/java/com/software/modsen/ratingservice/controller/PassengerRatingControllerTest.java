package com.software.modsen.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.ratingservice.controller.impl.PassengerRatingController;
import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(PassengerRatingController.class)
class PassengerRatingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PassengerRatingService passengerRatingService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPassengerRatings() throws Exception {
        when(passengerRatingService.getPassengerRatings()).thenReturn(getDefaultPassengerRatingResponseList());

        mockMvc.perform(get("/api/v1/ratings/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerRatingResponseList", hasSize(2)))
                .andExpect(jsonPath("$.passengerRatingResponseList[0].rating", is(DEFAULT_RATING)))
                .andExpect(jsonPath("$.passengerRatingResponseList[1].rating", is(UPDATED_RATING)));
    }

    @Test
    void testAddPassengerRating() throws Exception {
        when(passengerRatingService.addPassengerRating(Mockito.any(PassengerRatingRequest.class))).thenReturn(getDefaultPassengerRatingResponse());

        mockMvc.perform(post("/api/v1/ratings/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRatingRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(DEFAULT_ID.intValue())))
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testGetPassengerRatingById() throws Exception {
        when(passengerRatingService.getPassengerRatingById(DEFAULT_ID)).thenReturn(getDefaultPassengerRatingResponse());

        mockMvc.perform(get("/api/v1/ratings/passengers/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testDeletePassengerRating() throws Exception {
        doNothing().when(passengerRatingService).deletePassengerRating(DEFAULT_ID);

        mockMvc.perform(delete("/api/v1/ratings/passengers/{id}", DEFAULT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdatePassengerRating() throws Exception {
        when(passengerRatingService.updatePassengerRating(eq(DEFAULT_ID), any(PassengerRatingRequest.class))).thenReturn(getUpdatedPassengerRatingResponse());

        mockMvc.perform(put("/api/v1/ratings/passengers/{id}", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedPassengerRatingRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(UPDATED_RATING)));
    }
}
