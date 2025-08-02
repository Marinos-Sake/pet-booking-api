package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RoomInsertDTO {

    @NotBlank(message = "Room name is required")
    private String name;

    @NotNull(message = "Room type is required")
    private RoomType type;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotBlank(message = "Room description is required")
    private String description;

    private Boolean isAvailable = true;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "1.0", message = "Price must be at least 1.00")
    private BigDecimal pricePerNight;
}
