package com.software.modsen.rideservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RideListDto {
    private List<RideResponse> rideDtoList;
}