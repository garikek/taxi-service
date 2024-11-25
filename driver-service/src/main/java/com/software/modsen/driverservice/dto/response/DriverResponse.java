package com.software.modsen.driverservice.dto.response;

import lombok.Data;

@Data
public class DriverResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String vehicleNumber;
}
