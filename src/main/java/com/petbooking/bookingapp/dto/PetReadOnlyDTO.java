package com.petbooking.bookingapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petbooking.bookingapp.core.enums.Gender;
import com.petbooking.bookingapp.core.enums.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PetReadOnlyDTO {

    private Long id;

    private PetType type;

    private Gender gender;

    private String name;

    private Double weight;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String ownerFullName;
}
