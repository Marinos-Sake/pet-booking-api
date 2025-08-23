package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class PersonInsertDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @PastOrPresent(message = "The date of birth cannot be in the future.")
    @NotNull(message = "Date of Birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Place of Birth is required")
    private String placeOfBirth;

    @NotBlank(message = "Father name is required")
    private String fatherName;

    @NotBlank(message = "Identity number is required")
    private String identityNumber;

    @NotNull(message = "Gender is required")
    private Gender gender;

}
