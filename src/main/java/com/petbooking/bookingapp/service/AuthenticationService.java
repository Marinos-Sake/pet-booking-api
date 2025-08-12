package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppAuthenticationException;
import com.petbooking.bookingapp.dto.LoginResponseDTO;
import com.petbooking.bookingapp.repository.UserRepository;
import com.petbooking.bookingapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO authenticate(String username, String password) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppAuthenticationException("USR_", "Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppAuthenticationException("PWD_", "Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResponseDTO(token);
    }

}

