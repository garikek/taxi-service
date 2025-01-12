package com.software.modsen.authservice.controller.impl;

import com.software.modsen.authservice.controller.AuthApi;
import com.software.modsen.authservice.dto.request.LoginRequest;
import com.software.modsen.authservice.dto.request.RegisterRequest;
import com.software.modsen.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    @PostMapping("/register/passenger")
    public ResponseEntity<Void> registerPassenger(@RequestBody RegisterRequest registerRequest) {
        authService.registerPassenger(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PostMapping("/register/driver")
    public ResponseEntity<Void> registerDriver(@RequestBody RegisterRequest registerRequest) {
        authService.registerDriver(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        AccessTokenResponse tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}
