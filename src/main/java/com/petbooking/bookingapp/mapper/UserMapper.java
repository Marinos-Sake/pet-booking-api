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

    public User mapToUserEntity(UserInsertDTO dto) {

        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setIsActive(dto.getIsActive());

        //Nested Person
        PersonInsertDTO personInsertDTO = dto.getPerson();
        if (personInsertDTO != null) {
            Person person = new Person();
            person.setName(personInsertDTO.getName());
            person.setSurname(personInsertDTO.getSurname());
            person.setDateOfBirth(personInsertDTO.getDateOfBirth());
            person.setPlaceOfBirth(personInsertDTO.getPlaceOfBirth());
            person.setFatherName(personInsertDTO.getFatherName());
            person.setIdentityNumber(personInsertDTO.getIdentityNumber());
            person.setGender(personInsertDTO.getGender());

            user.setPerson(person);
        }

        return user;

    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        if (user == null) return null;

        UserReadOnlyDTO dto = new UserReadOnlyDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setIsActive(user.getIsActive());

        // Nested person
        Person person = user.getPerson();
        if (person != null) {
            PersonReadOnlyDTO personDTO = new PersonReadOnlyDTO();
            personDTO.setName(person.getName());
            personDTO.setSurname(person.getSurname());
            personDTO.setGender(person.getGender());
            personDTO.setDateOfBirth(person.getDateOfBirth());
            personDTO.setIdentityNumber(person.getIdentityNumber());
            personDTO.setFatherName(person.getFatherName());
            personDTO.setPlaceOfBirth(person.getPlaceOfBirth());
            dto.setPerson(personDTO);
        }

        return dto;
    }
}
