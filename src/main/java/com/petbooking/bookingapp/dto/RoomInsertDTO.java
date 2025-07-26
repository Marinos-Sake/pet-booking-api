package com.petbooking.bookingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomInsertDTO {

    @NotBlank(message = "Room name is required")
    private String name;

    @NotBlank(message = "Room type is required")
    private String type;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotBlank(message = "Room description is required")
    private String description;

    private Boolean isAvailable = true;
}
