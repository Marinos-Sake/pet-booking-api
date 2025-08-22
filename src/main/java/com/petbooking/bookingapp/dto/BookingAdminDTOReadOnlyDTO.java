package com.petbooking.bookingapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookingAdminDTOReadOnlyDTO {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private BigDecimal totalPrice;

    private Long userId;
    private Long petId;
    private Long roomId;
}
