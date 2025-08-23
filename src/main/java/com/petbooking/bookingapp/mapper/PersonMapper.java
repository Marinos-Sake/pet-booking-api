package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.PersonInsertDTO;
import com.petbooking.bookingapp.dto.PersonReadOnlyDTO;
import com.petbooking.bookingapp.dto.PersonUpdateDTO;
import com.petbooking.bookingapp.entity.Person;
import org.springframework.stereotype.Component;

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

        if (dto.getName() != null) person.setName(dto.getName());
        if (dto.getSurname() != null) person.setSurname(dto.getSurname());
        if (dto.getDateOfBirth() != null) person.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getPlaceOfBirth() != null) person.setPlaceOfBirth(dto.getPlaceOfBirth());
        if (dto.getFatherName() != null) person.setFatherName(dto.getFatherName());
        if (dto.getIdentityNumber() != null) person.setIdentityNumber(dto.getIdentityNumber());
        if (dto.getGender() != null) person.setGender(dto.getGender());
    }
}
