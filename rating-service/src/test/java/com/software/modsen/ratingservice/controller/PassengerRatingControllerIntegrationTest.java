package com.software.modsen.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.ratingservice.config.DatabaseContainerConfiguration;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
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

import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(DatabaseContainerConfiguration.class)
public class PassengerRatingControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PassengerRatingRepository passengerRatingRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPassengerRatings() throws Exception {
        passengerRatingRepository.saveAll(List.of(getDefaultPassengerRating(), getUpdatedPassengerRating()));

        mockMvc.perform(get("/api/v1/ratings/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerRatingResponseList", hasSize(2)))
                .andExpect(jsonPath("$.passengerRatingResponseList[0].rating", is(DEFAULT_RATING)))
                .andExpect(jsonPath("$.passengerRatingResponseList[1].rating", is(UPDATED_RATING)));
    }

    @Test
    void testAddPassengerRating() throws Exception {
        mockMvc.perform(post("/api/v1/ratings/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRatingRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testAddPassengerRating_Duplicate() throws Exception {
        passengerRatingRepository.save(getDefaultPassengerRating());

        mockMvc.perform(post("/api/v1/ratings/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRatingRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetPassengerRatingById_Success() throws Exception {
        PassengerRating passengerRating = passengerRatingRepository.save(getDefaultPassengerRating());

        mockMvc.perform(get("/api/v1/ratings/passengers/{id}", passengerRating.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testGetPassengerRatingById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/ratings/passengers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePassengerRating() throws Exception {
        PassengerRating passengerRating = passengerRatingRepository.save(getDefaultPassengerRating());

        mockMvc.perform(put("/api/v1/ratings/passengers/{id}", passengerRating.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedPassengerRatingRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(UPDATED_RATING)));
    }

    @Test
    void testUpdatePassengerRating_NotFound() throws Exception {
        mockMvc.perform(put("/api/v1/ratings/passengers/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedPassengerRatingRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePassengerRating() throws Exception {
        PassengerRating passengerRating = passengerRatingRepository.save(getDefaultPassengerRating());

        mockMvc.perform(delete("/api/v1/ratings/passengers/{id}", passengerRating.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePassengerRating_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/ratings/passengers/999"))
                .andExpect(status().isNotFound());
    }
}
