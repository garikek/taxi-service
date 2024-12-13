package com.software.modsen.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.ratingservice.controller.impl.DriverRatingController;
import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.service.DriverRatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.software.modsen.ratingservice.util.DriverRatingTestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(DriverRatingController.class)
class DriverRatingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverRatingService driverRatingService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDriverRatings() throws Exception {
        when(driverRatingService.getDriverRatings()).thenReturn(getDefaultDriverRatingResponseList());

        mockMvc.perform(get("/api/v1/ratings/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverRatingResponseList", hasSize(2)))
                .andExpect(jsonPath("$.driverRatingResponseList[0].rating", is(DEFAULT_RATING)))
                .andExpect(jsonPath("$.driverRatingResponseList[1].rating", is(UPDATED_RATING)));
    }

    @Test
    void testAddDriverRating() throws Exception {
        when(driverRatingService.addDriverRating(Mockito.any(DriverRatingRequest.class))).thenReturn(getDefaultDriverRatingResponse());

        mockMvc.perform(post("/api/v1/ratings/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRatingRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(DEFAULT_ID.intValue())))
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testGetDriverRatingById() throws Exception {
        when(driverRatingService.getDriverRatingById(DEFAULT_ID)).thenReturn(getDefaultDriverRatingResponse());

        mockMvc.perform(get("/api/v1/ratings/drivers/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testDeleteDriverRating() throws Exception {
        doNothing().when(driverRatingService).deleteDriverRating(DEFAULT_ID);

        mockMvc.perform(delete("/api/v1/ratings/drivers/{id}", DEFAULT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateDriverRating() throws Exception {
        when(driverRatingService.updateDriverRating(eq(DEFAULT_ID), any(DriverRatingRequest.class))).thenReturn(getUpdatedDriverRatingResponse());

        mockMvc.perform(put("/api/v1/ratings/drivers/{id}", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedDriverRatingRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(UPDATED_RATING)));
    }
}
