package com.software.modsen.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRideListDto {
    private List<AvailableRideResponse> availableRideDtoList;
}
