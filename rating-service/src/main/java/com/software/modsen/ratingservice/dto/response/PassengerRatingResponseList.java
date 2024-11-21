package com.software.modsen.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PassengerRatingResponseList {
    private List<PassengerRatingResponse> passengerRatingResponseList;
}
