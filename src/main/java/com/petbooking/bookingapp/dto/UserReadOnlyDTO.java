package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReadOnlyDTO {

    private Long id;

    private String username;

    private Boolean isActive;

    private Role role;

    private PersonReadOnlyDTO person;
}
