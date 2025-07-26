package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BookingReadOnlyDTO {

    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private BigDecimal totalPrice;

    private BookingStatus status;

    private String roomName;
    private String petName;
}
