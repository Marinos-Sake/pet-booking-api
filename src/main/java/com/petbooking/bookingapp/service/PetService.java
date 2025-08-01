package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppAccessDeniedException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.dto.PetInsertDTO;
import com.petbooking.bookingapp.dto.PetReadOnlyDTO;
import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.Pet;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.PetMapper;
import com.petbooking.bookingapp.repository.PetRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetMapper petMapper;

    @Transactional
    public PetReadOnlyDTO createPet(PetInsertDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_USER_NOT_FOUND",
                        "User with ID " + userId + " not found"));

        Person owner = user.getPerson();
        Pet pet = petMapper.mapToPetEntity(dto, owner);
        Pet savePet = petRepository.save(pet);
        return petMapper.mapToReadOnlyDTO(savePet);
    }

    public PetReadOnlyDTO getPetById(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_NOT_FOUND",
                        "Pet with ID " + petId + " not found"));

        return petMapper.mapToReadOnlyDTO(pet);
    }

    @Transactional
    public List<PetReadOnlyDTO> getAllPetsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_USER_NOT_FOUND",
                        "User with ID " + userId + " not found"
                ));
        Person owner = user.getPerson();
        List<Pet> pets = petRepository.findAllByOwner(owner);

        return pets.stream()
                .map(petMapper::mapToReadOnlyDTO)
                .toList();
    }

    @Transactional
    public PetReadOnlyDTO updatePet(Long petId, PetInsertDTO dto, Long userId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_NOT_FOUND",
                        "Pet with ID " + petId + " not found"));

        Long actualOwnerId = userRepository.findById(userId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_USER_NOT_FOUND",
                        "User with ID " + userId + " not found"))
                .getPerson().getId();

        if (!pet.getOwner().getId().equals(actualOwnerId)) {
            throw new AppAccessDeniedException(
                    "PET_ACCESS_DENIED",
                    "You do not have permission to modify this pet."
            );
        }
        petMapper.updatePetEntityFromDTO(dto, pet);
        Pet updatedPet = petRepository.save(pet);
        return petMapper.mapToReadOnlyDTO(updatedPet);

    }

    @Transactional
    public void deletePet(Long petId, Long userId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_NOT_FOUND",
                        "Pet with ID " + petId + " not found"));

        Long actualOwnerId = userRepository.findById(userId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_USER_NOT_FOUND",
                        "User with ID " + userId + " not found"))
                .getPerson().getId();

        if (!pet.getOwner().getId().equals(actualOwnerId)) {
            throw new AppAccessDeniedException(
                    "PET_ACCESS_DENIED",
                    "You do not have permission to delete this pet."
            );
        }

        petRepository.delete(pet);
    }

}
