package com.petbooking.bookingapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petbooking.bookingapp.core.enums.Gender;
import com.petbooking.bookingapp.core.validation.NullOrNotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonUpdateDTO {

    @NullOrNotBlank(message = "Name cannot be blank")
    private String name;

    @NullOrNotBlank(message = "Surname cannot be blank")
    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The date of birth cannot be in the future.")
    private LocalDate dateOfBirth;

    @NullOrNotBlank(message = "Place of Birth cannot be blank")
    private String placeOfBirth;

    @NullOrNotBlank(message = "Father name cannot be blank")
    private String fatherName;

    @NullOrNotBlank(message = "Identity number cannot be blank")
    private String identityNumber;

    private Gender gender;

}
