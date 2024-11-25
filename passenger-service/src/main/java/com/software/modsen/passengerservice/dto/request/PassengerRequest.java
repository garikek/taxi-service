package com.software.modsen.passengerservice.dto.request;

import lombok.Data;

@Data
public class PassengerRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
