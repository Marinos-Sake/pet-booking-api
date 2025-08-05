package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByBooking_User_Id(Long userId);

}
