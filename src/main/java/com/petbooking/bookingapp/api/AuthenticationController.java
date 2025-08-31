package com.petbooking.bookingapp.api;


import com.petbooking.bookingapp.dto.LoginRequestDTO;
import com.petbooking.bookingapp.dto.LoginResponseDTO;
import com.petbooking.bookingapp.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user login and authentication")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "[PUBLIC] Authenticate user",
            description = "Authenticates a user with username and password and returns a JWT access token."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

}


