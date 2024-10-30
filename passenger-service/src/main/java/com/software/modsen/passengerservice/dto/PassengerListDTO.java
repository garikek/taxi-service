package com.software.modsen.passengerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PassengerListDTO {
    private List<PassengerResponse> passengerDTOList;
}
