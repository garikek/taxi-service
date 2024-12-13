package com.software.modsen.passengerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.dto.response.PassengerListDTO;
import com.software.modsen.passengerservice.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.software.modsen.passengerservice.util.PassengerTestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(PassengerController.class)
class PassengerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PassengerService passengerService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPassengers() throws Exception {
        when(passengerService.getPassengers()).thenReturn(new PassengerListDTO(List.of(getDefaultPassengerResponse(), getUpdatedPassengerResponse())));

        mockMvc.perform(get("/api/v1/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerDTOList", hasSize(2)))
                .andExpect(jsonPath("$.passengerDTOList[0].name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.passengerDTOList[1].name", is(UPDATED_NAME)));
    }

    @Test
    void testAddPassenger() throws Exception {
        when(passengerService.addPassenger(Mockito.any(PassengerRequest.class))).thenReturn(getDefaultPassengerResponse());

        mockMvc.perform(post("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultPassengerRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(DEFAULT_PASSENGER_ID.intValue())))
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)));
    }

    @Test
    void testGetPassengerById() throws Exception {
        when(passengerService.getPassengerById(DEFAULT_PASSENGER_ID)).thenReturn(getDefaultPassengerResponse());

        mockMvc.perform(get("/api/v1/passengers/{id}", DEFAULT_PASSENGER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)));
    }

    @Test
    void testDeletePassenger() throws Exception {
        doNothing().when(passengerService).deletePassenger(DEFAULT_PASSENGER_ID);

        mockMvc.perform(delete("/api/v1/passengers/{id}", DEFAULT_PASSENGER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdatePassenger() throws Exception {
        when(passengerService.updatePassenger(eq(DEFAULT_PASSENGER_ID), any(PassengerRequest.class))).thenReturn(getUpdatedPassengerResponse());

        mockMvc.perform(put("/api/v1/passengers/{id}", DEFAULT_PASSENGER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedPassengerRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(UPDATED_NAME)))
                .andExpect(jsonPath("$.email", is(UPDATED_EMAIL)));
    }
}
