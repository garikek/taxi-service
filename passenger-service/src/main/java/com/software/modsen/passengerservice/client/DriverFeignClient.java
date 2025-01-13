package com.software.modsen.passengerservice.client;

import com.software.modsen.passengerservice.config.FeignClientConfiguration;
import com.software.modsen.passengerservice.dto.response.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign-client.driver-service.name}",
        path = "${feign-client.driver-service.path}",
        configuration = FeignClientConfiguration.class)
public interface DriverFeignClient {
    @GetMapping("/{driverId}")
    DriverResponse getDriverProfile(@PathVariable Long driverId);
}
