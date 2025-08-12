package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppAccessDeniedException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.core.exception.AppValidationException;
import com.petbooking.bookingapp.dto.BookingInsertDTO;
import com.petbooking.bookingapp.dto.BookingReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Pet;
import com.petbooking.bookingapp.entity.Room;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.BookingMapper;
import com.petbooking.bookingapp.repository.BookingRepository;
import com.petbooking.bookingapp.repository.PetRepository;
import com.petbooking.bookingapp.repository.RoomRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingReadOnlyDTO createBooking(BookingInsertDTO dto, Long userId) {
        // 1) Domain validation
        if (dto.getCheckInDate() == null || dto.getCheckOutDate() == null) {
            throw new AppValidationException("BOOK_DATES", "Both dates are required");
        }
        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        if (nights <= 0) {
            throw new AppValidationException("BOOK_DATES", "checkOut must be after checkIn");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_USER", "User not found"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_PET", "Pet not found"));

        if (!pet.getOwner().getId().equals(user.getPerson().getId())) {
            throw new AppAccessDeniedException("BOOK_PET_MISMATCH", "You don't own this pet");
        }

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_ROOM", "Room not found"));

        if (bookingRepository.existsByRoomIdAndDatesOverlap(
                dto.getRoomId(), dto.getCheckInDate(), dto.getCheckOutDate())) {
            throw new AppValidationException("ROOM_UNAVAILABLE",
                    "Room is already booked for the selected dates");
        }

        //Price
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        //Map and save
        Booking booking = bookingMapper.mapToBookingEntity(dto, user, pet, room, totalPrice);
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.mapToBookingReadOnlyDTO(saved);
    }


    public BookingReadOnlyDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_", "Booking not found"));

        return bookingMapper.mapToBookingReadOnlyDTO(booking);
    }


    public List<BookingReadOnlyDTO> getAllBookingByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppObjectNotFoundException("BOOK_USER", "User not found"));

        return bookingRepository.findAllByUser(user)
                .stream()
                .map(bookingMapper::mapToBookingReadOnlyDTO)
                .toList();
    }

    @Transactional
    public void deleteById(Long bookingId, User user) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_", "Booking not found"));

        boolean isOwner = booking.getUser().getId().equals(user.getId());
        boolean isAdmin = user.isAdmin();

        if (!isOwner && !isAdmin) {
            throw new AppAccessDeniedException("BOOK_", "You don't have permission to delete this booking");
        }

        bookingRepository.delete(booking);
    }

}
