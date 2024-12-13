package com.software.modsen.passengerservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
