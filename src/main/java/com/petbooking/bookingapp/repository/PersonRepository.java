package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
