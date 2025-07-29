package com.petbooking.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseMessageDTO {
    private final LocalDateTime timestamp;
    private final String code;
    private final String message;
}
