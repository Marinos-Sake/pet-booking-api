package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
