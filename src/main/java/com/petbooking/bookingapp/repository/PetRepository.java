package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
