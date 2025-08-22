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


    public PetReadOnlyDTO mapToReadOnlyDTO(Pet pet) {
        if (pet == null) return null;

        PetReadOnlyDTO dto = new PetReadOnlyDTO();
        dto.setId(pet.getId());
        dto.setType(pet.getType());
        dto.setGender(pet.getGender());
        dto.setName(pet.getName());
        dto.setWeight(pet.getWeight());
        dto.setBirthDate(pet.getBirthDate());

        // Optional: Owner’s full name (if available)
        if(pet.getOwner() != null) {
            dto.setOwnerFullName(
                    pet.getOwner().getName() + " " + pet.getOwner().getSurname()
            );
        }

        return dto;
    }

    public void updatePetEntityFromDTO(PetInsertDTO dto, Pet pet) {
        if (dto == null || pet == null) return;

        pet.setType(dto.getPetType());
        pet.setGender(dto.getGender());
        pet.setName(dto.getName());
        pet.setWeight(dto.getWeight());
        pet.setBirthDate(dto.getBirthDate());
    }


}
