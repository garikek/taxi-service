package com.software.modsen.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class KeycloakUserCreationException extends RuntimeException{
    public KeycloakUserCreationException(String message) {
        super(message);
    }
}
