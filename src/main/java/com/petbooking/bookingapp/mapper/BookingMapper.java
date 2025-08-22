package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.BookingAdminDTOReadOnlyDTO;
import com.petbooking.bookingapp.dto.BookingInsertDTO;
import com.petbooking.bookingapp.dto.BookingReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Pet;
import com.petbooking.bookingapp.entity.Room;
import com.petbooking.bookingapp.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BookingMapper {

    public Booking mapToBookingEntity(BookingInsertDTO dto, User user, Pet pet, Room room, BigDecimal totalPrice) {

        if (dto == null || user == null || pet == null || room == null) return null;

        Booking booking = new Booking();

        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setUser(user);
        booking.setPet(pet);
        booking.setRoom(room);
        booking.setTotalPrice(totalPrice);

        return booking;
    }

    public BookingReadOnlyDTO mapToBookingReadOnlyDTO(Booking booking) {

        if (booking == null) return null;

        BookingReadOnlyDTO dto = new BookingReadOnlyDTO();

        dto.setId(booking.getId());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());


        if (booking.getRoom() != null) {
            dto.setRoomName(booking.getRoom().getName());
        }

        if (booking.getPet() != null) {
            dto.setPetName(booking.getPet().getName());
        }

        return dto;
    }

    public BookingAdminDTOReadOnlyDTO toAdminDTO(Booking booking) {
        BookingAdminDTOReadOnlyDTO dto = new BookingAdminDTOReadOnlyDTO();

        dto.setId(booking.getId());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setStatus(booking.getStatus().name());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setUserId(booking.getUser()!=null ? booking.getUser().getId() : null);
        dto.setPetId(booking.getPet()!=null ? booking.getPet().getId() : null);
        dto.setRoomId(booking.getRoom()!=null ? booking.getRoom().getId() : null);
        return dto;
    }
}
