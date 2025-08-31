package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.*;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookings", description = "Endpoints for booking")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Operation(
            summary = "Create a new booking",
            description = "Creates a new booking for the authenticated user based on the provided booking details."
    )
    @PostMapping
    public ResponseEntity<BookingReadOnlyDTO> createBooking(
            @Valid @RequestBody BookingInsertDTO dto,
            @AuthenticationPrincipal User user
    ) {
        BookingReadOnlyDTO created = bookingService.createBooking(dto, user);
        return ResponseEntity.ok(created);
    }

    @Operation(
            summary = "Get my bookings",
            description = "Retrieves all bookings that belong to the currently authenticated user."
    )
    @GetMapping("/my")
    public ResponseEntity<List<BookingReadOnlyDTO>> getMyBookings(@AuthenticationPrincipal User user) {
        List<BookingReadOnlyDTO> bookings = bookingService.getAllBookingByUser(user);
        return ResponseEntity.ok(bookings);
    }

    @Operation(
            summary = "[ADMIN] Get booking by ID",
            description = "Retrieves a booking by its unique ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookingReadOnlyDTO> getBookingById(@PathVariable Long id) {
        BookingReadOnlyDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }


    @Operation(
            summary = "[ADMIN] Delete booking by ID",
            description = "Deletes a booking by its unique ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id, @AuthenticationPrincipal User user) {
        bookingService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "[PUBLIC] Get booking quote",
            description = "Calculates and returns a booking quote based on the provided request."
    )
    @PermitAll
    @PostMapping("/quote")
    public ResponseEntity<BookingQuoteResponseDTO> quote(@RequestBody BookingQuoteRequestDTO req) {
        return ResponseEntity.ok(bookingService.getQuote(req));
    }

    @Operation(
            summary = "[ADMIN] Get all bookings (admin)",
            description = "Retrieves a paginated list of all bookings with optional filters."
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingAdminReadOnlyDTO>> getAll(@ModelAttribute GenericFilters f) {
        return ResponseEntity.ok(bookingService.getAllForAdmin(f));
    }
}
