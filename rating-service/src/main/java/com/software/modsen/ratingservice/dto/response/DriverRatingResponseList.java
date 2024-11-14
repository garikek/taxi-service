package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DriverRatingResponseList {
    private List<DriverRatingResponse> driverRatingResponseList;
}
