package com.software.modsen.passengerservice.dto;

import lombok.Data;

@Data
public class PassengerResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Double rating;
}
