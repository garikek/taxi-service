package com.software.modsen.driverservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.modsen.driverservice.controller.impl.DriverController;
import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverListDto;
import com.software.modsen.driverservice.service.DriverService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.software.modsen.driverservice.util.DriverTestUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(DriverController.class)
class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverService driverService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDrivers() throws Exception {
        when(driverService.getDrivers()).thenReturn(new DriverListDto(List.of(getDefaultDriverResponse(), getUpdatedDriverResponse())));

        mockMvc.perform(get("/api/v1/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverDtoList", hasSize(2)))
                .andExpect(jsonPath("$.driverDtoList[0].name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.driverDtoList[1].name", is(UPDATED_NAME)));
    }

    @Test
    void testAddDriver() throws Exception {
        when(driverService.addDriver(Mockito.any(DriverRequest.class))).thenReturn(getDefaultDriverResponse());

        mockMvc.perform(post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDefaultDriverRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(DEFAULT_DRIVER_ID.intValue())))
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.vehicleNumber", is(DEFAULT_VEHICLE_NUMBER)));
    }

    @Test
    void testGetDriverById() throws Exception {
        when(driverService.getDriverById(DEFAULT_DRIVER_ID)).thenReturn(getDefaultDriverResponse());

        mockMvc.perform(get("/api/v1/drivers/{id}", DEFAULT_DRIVER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DEFAULT_NAME)))
                .andExpect(jsonPath("$.email", is(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.vehicleNumber", is(DEFAULT_VEHICLE_NUMBER)));
    }

    @Test
    void testDeleteDriver() throws Exception {
        doNothing().when(driverService).deleteDriver(DEFAULT_DRIVER_ID);

        mockMvc.perform(delete("/api/v1/drivers/{id}", DEFAULT_DRIVER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateDriver() throws Exception {
        when(driverService.updateDriver(eq(DEFAULT_DRIVER_ID), any(DriverRequest.class))).thenReturn(getUpdatedDriverResponse());

        mockMvc.perform(put("/api/v1/drivers/{id}", DEFAULT_DRIVER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUpdatedDriverRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(UPDATED_NAME)))
                .andExpect(jsonPath("$.email", is(UPDATED_EMAIL)))
                .andExpect(jsonPath("$.vehicleNumber", is(UPDATED_VEHICLE_NUMBER)));
    }
}
