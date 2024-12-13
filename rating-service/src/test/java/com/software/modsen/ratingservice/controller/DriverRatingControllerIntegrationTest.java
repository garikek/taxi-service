package com.software.modsen.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.ratingservice.config.DatabaseContainerConfiguration;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
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

import static com.software.modsen.ratingservice.util.DriverRatingTestUtil.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(DatabaseContainerConfiguration.class)
public class DriverRatingControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DriverRatingRepository driverRatingRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDriverRatings() throws Exception {
        driverRatingRepository.saveAll(List.of(getDefaultDriverRating(), getUpdatedDriverRating()));

        mockMvc.perform(get("/api/v1/ratings/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverRatingResponseList", hasSize(2)))
                .andExpect(jsonPath("$.driverRatingResponseList[0].rating", is(DEFAULT_RATING)))
                .andExpect(jsonPath("$.driverRatingResponseList[1].rating", is(UPDATED_RATING)));
    }

    @Test
    void testAddDriverRating() throws Exception {
        mockMvc.perform(post("/api/v1/ratings/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRatingRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testAddDriverRating_Duplicate() throws Exception {
        driverRatingRepository.save(getDefaultDriverRating());

        mockMvc.perform(post("/api/v1/ratings/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRatingRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetDriverRatingById_Success() throws Exception {
        DriverRating driverRating = driverRatingRepository.save(getDefaultDriverRating());

        mockMvc.perform(get("/api/v1/ratings/drivers/{id}", driverRating.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(DEFAULT_RATING)));
    }

    @Test
    void testGetDriverRatingById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/ratings/drivers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateDriverRating() throws Exception {
        DriverRating driverRating = driverRatingRepository.save(getDefaultDriverRating());

        mockMvc.perform(put("/api/v1/ratings/drivers/{id}", driverRating.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedDriverRatingRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(UPDATED_RATING)));
    }

    @Test
    void testUpdateDriverRating_NotFound() throws Exception {
        mockMvc.perform(put("/api/v1/ratings/drivers/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedDriverRatingRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDriverRating() throws Exception {
        DriverRating driverRating = driverRatingRepository.save(getDefaultDriverRating());

        mockMvc.perform(delete("/api/v1/ratings/drivers/{id}", driverRating.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteDriverRating_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/ratings/drivers/999"))
                .andExpect(status().isNotFound());
    }
}
