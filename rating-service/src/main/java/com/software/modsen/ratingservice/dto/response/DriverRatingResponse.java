package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRatingResponse {
    private Long id;
    private Long driverId;
    private Double rating;
}
