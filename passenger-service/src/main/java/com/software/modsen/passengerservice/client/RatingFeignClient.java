package com.software.modsen.passengerservice.client;

import com.software.modsen.passengerservice.config.FeignClientConfiguration;
import com.software.modsen.passengerservice.dto.response.PassengerRatingResponseList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign-client.rating-service.name}",
        path = "${feign-client.rating-service.path}",
        configuration = FeignClientConfiguration.class)
public interface RatingFeignClient {
    @GetMapping("/list/{passengerId}")
    PassengerRatingResponseList getPassengerRatings(@PathVariable Long passengerId);
}
