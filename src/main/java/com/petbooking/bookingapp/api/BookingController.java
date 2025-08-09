package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.BookingInsertDTO;
import com.petbooking.bookingapp.dto.BookingReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingReadOnlyDTO> createBooking(
            @Valid @RequestBody BookingInsertDTO dto,
            @AuthenticationPrincipal User user
    ) {
        BookingReadOnlyDTO created = bookingService.createBooking(dto, user.getId());
        return ResponseEntity.ok(created);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingReadOnlyDTO>> getMyBookings(@AuthenticationPrincipal User user) {
        List<BookingReadOnlyDTO> bookings = bookingService.getAllBookingByUser(user.getId());
        return ResponseEntity.ok(bookings);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookingReadOnlyDTO> getBookingById(@PathVariable Long id) {
        BookingReadOnlyDTO booking = bookingService.getBookingById(id); // χωρίς userId
        return ResponseEntity.ok(booking);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id,
                                              @AuthenticationPrincipal User user) {
        bookingService.deleteById(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
