package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.PetInsertDTO;
import com.petbooking.bookingapp.dto.PetReadOnlyDTO;
import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public Pet mapToPetEntity(PetInsertDTO dto, Person owner) {
        if (dto == null || owner == null) return null;

        Pet pet = new Pet();
        pet.setType(dto.getPetType());
        pet.setGender(dto.getGender());
        pet.setName(dto.getName());
        pet.setWeight(dto.getWeight());
        pet.setBirthDate(dto.getBirthDate());
        pet.setOwner(owner);

        return pet;

    }


    public PetReadOnlyDTO mapToPetReadOnlyDTO(Pet pet) {
        if (pet == null) return null;

        PetReadOnlyDTO dto = new PetReadOnlyDTO();
        dto.setId(dto.getId());
        dto.setType(dto.getType());
        dto.setGender(dto.getGender());
        dto.setName(dto.getName());
        dto.setWeight(dto.getWeight());

        // Optional: Owner’s full name (if available)
        if(pet.getOwner() != null) {
            dto.setOwnerFullName(
                    pet.getOwner().getName() + " " + pet.getOwner().getSurname()
            );
        }

        return dto;
    }


}
