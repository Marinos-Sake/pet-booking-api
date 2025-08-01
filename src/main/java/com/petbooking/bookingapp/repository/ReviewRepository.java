package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
