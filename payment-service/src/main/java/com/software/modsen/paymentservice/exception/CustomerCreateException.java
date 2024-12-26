package com.software.modsen.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerCreateException extends RuntimeException {
    public CustomerCreateException(String message) {
        super(message);
    }
}
