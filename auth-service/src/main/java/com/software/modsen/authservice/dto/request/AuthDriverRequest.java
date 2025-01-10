package com.software.modsen.authservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDriverRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String vehicleNumber;
    private String action;
}
