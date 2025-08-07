package com.petbooking.bookingapp.security;

import com.petbooking.bookingapp.core.exception.AppAuthenticationException;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppAuthenticationException("USR_", "User not found with username: " + username));
    }


}
