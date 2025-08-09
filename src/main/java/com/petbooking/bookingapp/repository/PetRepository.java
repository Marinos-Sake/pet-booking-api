package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwner(Person owner);

    Optional<Pet> findByIdAndOwnerId(Long id, Long ownerId);
}
