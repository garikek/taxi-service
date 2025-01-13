package com.software.modsen.passengerservice.client;

import com.software.modsen.passengerservice.config.FeignClientConfiguration;
import com.software.modsen.passengerservice.dto.response.RideListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign-client.ride-service.name}",
        path = "${feign-client.ride-service.path}",
        configuration = FeignClientConfiguration.class)
public interface RideFeignClient {
    @GetMapping("/price/{rideId}")
    Double getEstimatedRidePrice(@PathVariable Long rideId);

    @GetMapping("/passenger/{passengerId}")
    RideListDto getRideHistory(@PathVariable Long passengerId);
}
