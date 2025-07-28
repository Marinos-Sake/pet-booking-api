package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.PaymentInsertDTO;
import com.petbooking.bookingapp.dto.PaymentReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    public Payment mapToPaymentEntity(PaymentInsertDTO dto, Booking booking) {

        if (dto == null) return null;

        Payment payment = new Payment();

        payment.setAmount(dto.getAmount());
        payment.setBooking(booking);
        return payment;
    }


    public PaymentReadOnlyDTO mapToReadOnlyDTO(Payment payment) {
        if (payment == null) return null;

        PaymentReadOnlyDTO dto = new PaymentReadOnlyDTO();

        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate().toLocalDate());
        dto.setBookingId(payment.getBooking().getId());

        if (payment.getBooking().getUser() != null && payment.getBooking().getUser().getPerson() != null) {
            var person = payment.getBooking().getUser().getPerson();
            dto.setUserFullName(person.getName() + " " + person.getSurname());
        }

        if (payment.getBooking().getPet() != null) {
            dto.setPetName(payment.getBooking().getPet().getName());
        }

        if (payment.getBooking().getRoom() != null) {
            dto.setRoomName(payment.getBooking().getRoom().getName());
        }

        return dto;



    }
}
