package com.petbooking.bookingapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petbooking.bookingapp.core.enums.Gender;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonUpdateDTO {

    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The date of birth cannot be in the future.")
    private LocalDate dateOfBirth;

    private String placeOfBirth;

    private String fatherName;

    private String identityNumber;

    private Gender gender;

}
