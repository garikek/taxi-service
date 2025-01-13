package com.software.modsen.driverservice.client;

import com.software.modsen.driverservice.config.FeignClientConfiguration;
import com.software.modsen.driverservice.dto.response.RideListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign-client.ride-service.name}",
        path = "${feign-client.ride-service.path}",
        configuration = FeignClientConfiguration.class)
public interface RideFeignClient {
    @GetMapping("/driver/{driverId}")
    RideListDto getRideHistory(@PathVariable Long driverId);
}
