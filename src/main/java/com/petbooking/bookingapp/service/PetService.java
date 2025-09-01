package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.core.exception.AppValidationException;
import com.petbooking.bookingapp.dto.PetInsertDTO;
import com.petbooking.bookingapp.dto.PetReadOnlyDTO;
import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.Pet;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.PetMapper;
import com.petbooking.bookingapp.repository.BookingRepository;
import com.petbooking.bookingapp.repository.PetRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetMapper petMapper;
    private final BookingRepository bookingRepository;

    @Transactional
    public PetReadOnlyDTO createPet(PetInsertDTO dto, User user) {
        Person owner = user.getPerson();
        Pet pet = petMapper.mapToPetEntity(dto, owner);
        Pet savePet = petRepository.save(pet);
        return petMapper.mapToReadOnlyDTO(savePet);
    }

    @Transactional(readOnly = true)
    public PetReadOnlyDTO getPetById(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "PET_",
                        "Pet not found"));

        return petMapper.mapToReadOnlyDTO(pet);
    }

    @Transactional(readOnly = true)
    public List<PetReadOnlyDTO> getAllPetsByUserId(User user) {
        Person owner = user.getPerson();
        List<Pet> pets = petRepository.findAllByOwner(owner);

        return pets.stream()
                .map(petMapper::mapToReadOnlyDTO)
                .toList();
    }

    @Transactional
    public void deletePet(Long petId, User user) {

        Person owner = user.getPerson();

        Pet pet = petRepository.findByIdAndOwner(petId, owner)
                .orElseThrow(() -> new AppObjectNotFoundException("PET_", "Pet not found"));

        boolean hasBookings = bookingRepository.existsByPetId(petId);

        if (hasBookings) {
            throw new AppValidationException("PET_BOOKING_", "The pet is associated with a booking and cannot be deleted.");
        }

        petRepository.delete(pet);
    }

}
