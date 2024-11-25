package com.software.modsen.passengerservice.dto.response;

import lombok.Data;

@Data
public class PassengerResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
