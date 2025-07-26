package com.petbooking.bookingapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RoomReadOnlyDTO {

    private Long id;

    private String name;

    private String type;

    private Integer capacity;

    private BigDecimal pricePerNight;

    private String description;

    private Boolean isAvailable;
}