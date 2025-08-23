package com.petbooking.bookingapp.mapper;


import com.petbooking.bookingapp.core.exception.AppValidationException;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.dto.UserUpdateDTO;
import com.petbooking.bookingapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final PersonMapper personMapper;

    public User mapToUserEntity(UserInsertDTO dto) {

        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        //Nested Person
        user.setPerson(personMapper.mapToPersonEntity(dto.getPerson()));

        return user;

    }

    public UserReadOnlyDTO mapToReadOnlyDTO(User user) {
        if (user == null) return null;

        UserReadOnlyDTO dto = new UserReadOnlyDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setIsActive(user.getIsActive());

        // Nested person
        dto.setPerson(personMapper.mapToReadOnlyDTO(user.getPerson()));
        return dto;
    }


    public void updateUserEntityFromDTO(UserUpdateDTO dto, User user) {
        if (dto == null || user == null) return;

        if (hasText(dto.getUsername())) user.setUsername(dto.getUsername().trim());

        if (hasText(dto.getPassword())) {
            String raw = dto.getPassword().trim();
            if (user.getPassword() == null || !passwordEncoder.matches(raw, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(raw));
            }
        }

        if (dto.getPerson() != null) {
            if (user.getPerson() == null) {
                throw new AppValidationException("USR_", "There is no Person available for update.");
            }
            personMapper.updatePersonEntityFromDTO(dto.getPerson(), user.getPerson());
        }
    }


}

