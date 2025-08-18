package com.petbooking.bookingapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingQuoteRequest {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
}