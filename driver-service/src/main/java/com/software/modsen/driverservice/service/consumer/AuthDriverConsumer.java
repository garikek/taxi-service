package com.software.modsen.driverservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.driverservice.dto.request.AuthDriverRequest;
import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.software.modsen.driverservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDriverConsumer {
    private final DriverService driverService;

    @RabbitListener(queues = "${driver-service.auth.queue}", ackMode = "MANUAL")
    public void handleAuthDriverMessage(AuthDriverRequest message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "CREATE":
                    DriverRequest driverRequest = DriverRequest.builder()
                            .name(message.getName())
                            .email(message.getEmail())
                            .phoneNumber(message.getPhoneNumber())
                            .vehicleNumber(message.getVehicleNumber())
                            .build();
                    driverService.addDriver(driverRequest);
                    break;
                default:
                    log.warn(UNKNOWN_ACTION_MESSAGE, message.getAction());
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error(ERROR_PROCESSING_MESSAGE, e.getMessage());
            channel.basicReject(tag, false);
        }
    }
}
