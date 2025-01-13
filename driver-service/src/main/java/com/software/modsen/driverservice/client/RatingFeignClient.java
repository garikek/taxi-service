package com.software.modsen.driverservice.client;

import com.software.modsen.driverservice.config.FeignClientConfiguration;
import com.software.modsen.driverservice.dto.response.DriverRatingResponseList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign-client.rating-service.name}",
        path = "${feign-client.rating-service.path}",
        configuration = FeignClientConfiguration.class)
public interface RatingFeignClient {
    @GetMapping("/list/{driverId}")
    DriverRatingResponseList getDriverRatings(@PathVariable Long driverId);
}
