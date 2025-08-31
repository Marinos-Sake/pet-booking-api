package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppAccessDeniedException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.core.exception.AppValidationException;
import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.*;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Pet;
import com.petbooking.bookingapp.entity.Room;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.BookingMapper;
import com.petbooking.bookingapp.repository.BookingRepository;
import com.petbooking.bookingapp.repository.PetRepository;
import com.petbooking.bookingapp.repository.RoomRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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
    public BookingReadOnlyDTO createBooking(BookingInsertDTO dto, User user) {

        // 1) Domain validation
        if (dto.getCheckInDate() == null || dto.getCheckOutDate() == null) {
            throw new AppValidationException("BOOK_DATES_", "Both dates are required");
        }

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());

        if (nights <= 0) {
            throw new AppValidationException("BOOK_DATES_", "Check-out date must be after check-in date");
        }

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_PET_", "Pet not found"));

        if (!pet.getOwner().getId().equals(user.getPerson().getId())) {
            // Ensure user is booking only with their own pet
            throw new AppAccessDeniedException("BOOK_PET_MISMATCH_", "You don't own this pet");
        }

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_ROOM_", "Room not found"));


        if (!Boolean.TRUE.equals(room.getIsAvailable())) {
            // Check that the room is marked as available before booking
            throw new AppValidationException("ROOM_NOT_AVAILABLE_",
                    "The room is unavailable and cannot be booked.");
        }

        if (bookingRepository.existsByRoomIdAndDatesOverlap(
                dto.getRoomId(), dto.getCheckInDate(), dto.getCheckOutDate())) {
            throw new AppValidationException("ROOM_UNAVAILABLE_",
                    "Room is already booked for the selected dates");
        }

        //Calculate total price = price per night * number of nights
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

    @Transactional(readOnly = true)
    public Page<BookingAdminReadOnlyDTO> getAllForAdmin(GenericFilters filters) {
        return bookingRepository.findAll(filters.toPageable())
                .map(bookingMapper::toAdminDTO);
    }


    public List<BookingReadOnlyDTO> getAllBookingByUser(User user) {
        return bookingRepository.findAllByUser(user)
                .stream()
                .map(bookingMapper::mapToBookingReadOnlyDTO)
                .toList();
    }

    @Transactional
    public void deleteById(Long bookingId, User user) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_", "Booking not found"));

        bookingRepository.delete(booking);
    }

    @Transactional(readOnly= true)
    public BookingQuoteResponseDTO getQuote(BookingQuoteRequestDTO req) {

        if (req.getCheckInDate() == null || req.getCheckOutDate() == null) {
            throw new AppValidationException("BOOK_QUOTE_DATES_", "Both dates are required");
        }

        // Compute nights and ensure checkout is strictly after check-in
        long nights = ChronoUnit.DAYS.between(req.getCheckInDate(), req.getCheckOutDate());
        if (nights <= 0) {
            throw new AppValidationException("BOOK_QUOTE_DATES_", "checkOut must be after checkIn");
        }

        Room room = roomRepository.findById(req.getRoomId())
                .orElseThrow(() -> new AppObjectNotFoundException("BOOK_QUOTE_ROOM_", "Room not found"));

        // Calculate total price = price per night * nights
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        // Return quote with nights, unit price, and total
        return new BookingQuoteResponseDTO(nights, room.getPricePerNight(), totalPrice);
    }


    @Transactional(readOnly = true)
    public List<CalendarDisabledRangeDTO> getDisabledRangesForRoom(Long roomId, LocalDate from, LocalDate to) {
        // Validation
        if (from == null || to == null) {
            throw new AppValidationException("CAL_DATES_", "Both dates are required");
        }
        if (!to.isAfter(from)) {
            throw new AppValidationException("CAL_DATES_", "to must be after from");
        }

        // Ensure room exists
        roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("CAL_ROOM_", "Room not found"));

        // Fetch overlapping bookings for the given window
        List<Booking> overlaps = bookingRepository.findOverlaps(roomId, from, to);

        // 5. Map each Booking to a DisabledRangeDTO (unavailable date range)
        //    - start = check-in date
        //    - endInclusive = check-out - 1 (because on the check-out day the room becomes available again)
        List<CalendarDisabledRangeDTO> ranges = overlaps.stream()
                .map(b -> {
                    LocalDate start = b.getCheckInDate();
                    LocalDate endInclusive = b.getCheckOutDate().minusDays(1);
                    if (endInclusive.isBefore(start)) endInclusive = start;
                    return new CalendarDisabledRangeDTO(start, endInclusive);
                })
                .sorted(Comparator.comparing(CalendarDisabledRangeDTO::getFrom))
                .toList();

        List<CalendarDisabledRangeDTO> merged = new ArrayList<>();
        for (CalendarDisabledRangeDTO current : ranges) {
            // if this is the first interval, add it
            if (merged.isEmpty()) {
                merged.add(current);
                continue;
            }
            // Get the last merged interval so far
            CalendarDisabledRangeDTO last = merged.get(merged.size() - 1);

            // Check if the current interval overlaps or directly touches the last one
            boolean touchesOrOverlaps = !current.getFrom().isAfter(last.getTo().plusDays(1));
            if (touchesOrOverlaps) {
                // Merge them: keep the same 'from' but extend 'to' to the max of both
                LocalDate newTo = last.getTo().isAfter(current.getTo()) ? last.getTo() : current.getTo();
                merged.set(merged.size() - 1, new CalendarDisabledRangeDTO(last.getFrom(), newTo));
            } else {
                // Otherwise, no overlap -> add as a new independent interval
                merged.add(current);
            }
        }

        return merged;
    }


}
