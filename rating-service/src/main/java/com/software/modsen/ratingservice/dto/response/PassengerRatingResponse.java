package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRatingResponse {
    private Long id;
    private Long passengerId;
    private Double rating;
}
