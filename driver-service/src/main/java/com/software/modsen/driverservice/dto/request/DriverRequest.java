package com.software.modsen.driverservice.dto.request;

import lombok.Data;

@Data
public class DriverRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String vehicleNumber;
}
