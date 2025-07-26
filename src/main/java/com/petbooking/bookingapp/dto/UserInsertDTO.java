package com.petbooking.bookingapp.dto;

import com.petbooking.bookingapp.core.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;

@Getter
@Setter
@NoArgsConstructor
public class UserInsertDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    private Boolean isActive = true;

    @NotNull(message = "Person info is required")
    private PersonInsertDTO person;
}
