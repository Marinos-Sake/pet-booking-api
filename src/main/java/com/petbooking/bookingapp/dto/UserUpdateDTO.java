package com.petbooking.bookingapp.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private String username;

    private String password;

    @Valid
    private PersonUpdateDTO person;
}
