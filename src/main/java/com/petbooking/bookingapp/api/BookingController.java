package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.BookingInsertDTO;
import com.petbooking.bookingapp.dto.BookingReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingReadOnlyDTO> createBooking(@Valid @RequestBody BookingInsertDTO dto,
                                                            @RequestParam("userId") Long userId) {
        BookingReadOnlyDTO created = bookingService.createBooking(dto, userId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingReadOnlyDTO>> getMyBookings(@RequestParam("userId") Long userId) {
        List<BookingReadOnlyDTO> bookings = bookingService.getAllBookingByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingReadOnlyDTO> getBookingById(@PathVariable Long id,
                                                             @RequestParam("userId") Long userId) {
        BookingReadOnlyDTO booking = bookingService.getBookingById(id, userId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id,
                                              @RequestParam("userId") Long userId) {
        bookingService.deleteById(id, userId);
        return ResponseEntity.noContent().build();
    }
}
