package com.software.modsen.authservice.exception.handler;

import com.software.modsen.authservice.exception.InvalidResourceException;
import com.software.modsen.authservice.exception.KeycloakUserCreationException;
import com.software.modsen.authservice.exception.WrongCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.software.modsen.authservice.utility.Constant.UNEXPECTED_ERROR_MESSAGE;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<String> handleWrongCredentialsException(WrongCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({KeycloakUserCreationException.class, InvalidResourceException.class})
    public ResponseEntity<String> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        String errorMessage = UNEXPECTED_ERROR_MESSAGE + e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
