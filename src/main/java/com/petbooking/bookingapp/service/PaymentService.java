package com.petbooking.bookingapp.service;


import com.petbooking.bookingapp.core.enums.BookingStatus;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.dto.PaymentInsertDTO;
import com.petbooking.bookingapp.dto.PaymentReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Payment;
import com.petbooking.bookingapp.mapper.PaymentMapper;
import com.petbooking.bookingapp.repository.BookingRepository;
import com.petbooking.bookingapp.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;


    @Transactional
    public PaymentReadOnlyDTO addPayment(PaymentInsertDTO dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new AppObjectNotFoundException("PAY_BOOKING", "Booking not found"));

        Payment payment = paymentMapper.mapToPaymentEntity(dto, booking);
        Payment savedPayment = paymentRepository.save(payment);

        updateBookingStatusAfterPayment(booking);

        return paymentMapper.mapToReadOnlyDTO(savedPayment);
    }

    public List<PaymentReadOnlyDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::mapToReadOnlyDTO)
                .toList();
    }

    private void updateBookingStatusAfterPayment(Booking booking) {
        BigDecimal totalPaid = booking.getPayments()
                .stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDue = booking.getTotalPrice();

        if (totalPaid.compareTo(BigDecimal.ZERO) > 0 && totalPaid.compareTo(totalDue) < 0) {
            booking.setStatus(BookingStatus.CONFIRMED);
        } else if (totalPaid.compareTo(totalDue) >= 0) {
            booking.setStatus(BookingStatus.COMPLETED);
        }

        bookingRepository.save(booking);
    }

    public PaymentReadOnlyDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("PAY_ID", "Payment not found"));

        return paymentMapper.mapToReadOnlyDTO(payment);
    }

    public List<PaymentReadOnlyDTO> getMyAllPayments(Long userId) {
        return paymentRepository.findAllByBooking_User_Id(userId)
                .stream()
                .map(paymentMapper::mapToReadOnlyDTO)
                .toList();
    }
}
