package com.software.modsen.driverservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RideListDto {
    private List<RideResponse> rideDtoList;
}
