package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.PersonInsertDTO;
import com.petbooking.bookingapp.dto.PersonReadOnlyDTO;
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

    public void updatePersonEntityFromDTO(PersonInsertDTO dto, Person person) {
        if (dto == null || person == null) return;

        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setDateOfBirth(dto.getDateOfBirth());
        person.setPlaceOfBirth(dto.getPlaceOfBirth());
        person.setFatherName(dto.getFatherName());
        person.setIdentityNumber(dto.getIdentityNumber());
        person.setGender(dto.getGender());
    }


}
