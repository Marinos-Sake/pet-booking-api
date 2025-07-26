package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PersonReadOnlyDTO {

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String placeOfBirth;

    private String fatherName;

    private String identityNumber;

    private Gender gender;
}
