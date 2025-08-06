package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.PersonInsertDTO;
import com.petbooking.bookingapp.dto.PersonReadOnlyDTO;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        user.setIsActive(dto.getIsActive());

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
//
        return dto;
    }


    public void updateUserEntityFromDTO(UserInsertDTO dto, User user) {
        user.setUsername(dto.getUsername());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getPerson() != null && user.getPerson() != null) {
            personMapper.updatePersonEntityFromDTO(dto.getPerson(), user.getPerson());
        }

        if (!dto.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

    }

}
