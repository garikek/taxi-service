package com.software.modsen.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DriverListDto {
    private List<DriverResponse> driverDtoList;
}
