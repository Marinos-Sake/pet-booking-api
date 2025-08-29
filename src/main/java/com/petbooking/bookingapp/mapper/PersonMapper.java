package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.PersonInsertDTO;
import com.petbooking.bookingapp.dto.PersonReadOnlyDTO;
import com.petbooking.bookingapp.dto.PersonUpdateDTO;
import com.petbooking.bookingapp.entity.Person;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
public class PersonMapper {

    public Person mapToPersonEntity(PersonInsertDTO dto) {

        if (dto == null) return null;

        Person person = new Person();
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setDateOfBirth(dto.getDateOfBirth());
        person.setPlaceOfBirth(dto.getPlaceOfBirth());
        person.setFatherName(dto.getFatherName());
        person.setIdentityNumber(dto.getIdentityNumber());
        person.setGender(dto.getGender());

        return person;

    }

    public PersonReadOnlyDTO mapToReadOnlyDTO(Person person) {

        if (person == null) return null;

        PersonReadOnlyDTO dto = new PersonReadOnlyDTO();

        dto.setName(person.getName());
        dto.setSurname(person.getSurname());
        dto.setDateOfBirth(person.getDateOfBirth());
        dto.setPlaceOfBirth(person.getPlaceOfBirth());
        dto.setFatherName(person.getFatherName());
        dto.setIdentityNumber(person.getIdentityNumber());
        dto.setGender(person.getGender());

        return dto;


    }

    public void updatePersonEntityFromDTO(PersonUpdateDTO dto, Person person) {
        if (dto == null || person == null) return;


        if (hasText(dto.getName()))         person.setName(dto.getName().trim());
        if (hasText(dto.getSurname()))      person.setSurname(dto.getSurname().trim());
        if (dto.getDateOfBirth() != null)   person.setDateOfBirth(dto.getDateOfBirth());
        if (hasText(dto.getPlaceOfBirth())) person.setPlaceOfBirth(dto.getPlaceOfBirth().trim());
        if (hasText(dto.getFatherName()))   person.setFatherName(dto.getFatherName().trim());
        if (hasText(dto.getIdentityNumber())) person.setIdentityNumber(dto.getIdentityNumber().trim());
        if (dto.getGender() != null)        person.setGender(dto.getGender());

    }
}
