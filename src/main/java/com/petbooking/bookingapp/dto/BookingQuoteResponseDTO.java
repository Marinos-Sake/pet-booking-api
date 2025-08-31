package com.petbooking.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BookingQuoteResponseDTO {
    private long nights;
    private BigDecimal pricePerNight;
    private BigDecimal totalPrice;
}
