package com.software.modsen.driverservice.config;

import com.software.modsen.driverservice.exception.FeignClientException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        String[] responseMessageSplit = exception.getMessage().split("\"message\"");
        String[] exMessageSplit = responseMessageSplit[responseMessageSplit.length - 1].split("\"");
        String exMessage = exMessageSplit[exMessageSplit.length - 2];
        if (status == 400) {
            return new FeignClientException(exMessage);
        }
        return exception;
    }
}
