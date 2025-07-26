package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.Gender;
import com.petbooking.bookingapp.core.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PetInsertDTO {

    @NotNull(message = "Pet type is required")
    private PetType petType;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Weight is required")
    private Double weight;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

}
