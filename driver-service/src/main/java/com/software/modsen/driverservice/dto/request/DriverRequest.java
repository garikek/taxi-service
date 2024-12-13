package com.software.modsen.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String vehicleNumber;
}
