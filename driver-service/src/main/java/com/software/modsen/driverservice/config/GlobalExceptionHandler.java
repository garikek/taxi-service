package com.software.modsen.driverservice.config;

import com.software.modsen.driverservice.exception.FeignClientException;
import com.software.modsen.driverservice.exception.ResourceNotFoundException;
import com.software.modsen.driverservice.exception.InvalidResourceException;
import com.software.modsen.driverservice.exception.DuplicateResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.software.modsen.driverservice.utility.Constant.AUTHORIZE_EXCEPTION_MESSAGE;
import static com.software.modsen.driverservice.utility.Constant.UNEXPECTED_ERROR_MESSAGE;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({InvalidResourceException.class, FeignClientException.class})
    public ResponseEntity<String> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(AUTHORIZE_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(UNEXPECTED_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
