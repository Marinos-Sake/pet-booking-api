package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
