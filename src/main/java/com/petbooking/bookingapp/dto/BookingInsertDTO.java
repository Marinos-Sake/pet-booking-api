package com.petbooking.bookingapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BookingInsertDTO {

    @NotNull(message = "Check-in date is required")
    @Future(message = "Check-in must be in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out must be in the future")
    private LocalDate checkOutDate;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Pet ID is required")
    private Long petId;
}

