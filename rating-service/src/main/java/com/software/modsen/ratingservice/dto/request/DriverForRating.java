package com.software.modsen.ratingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverForRating {
    private Long driverId;
    private Double rating;
    private String action;
}
