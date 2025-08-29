package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.validation.NullOrNotBlank;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    @NullOrNotBlank(message = "username cannot be blank")
    private String username;

    @NullOrNotBlank(message = "password cannot be blank")
    private String password;

    @Valid
    private PersonUpdateDTO person;
}
