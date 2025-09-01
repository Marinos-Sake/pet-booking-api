package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.CalendarDisabledRangeDTO;
import com.petbooking.bookingapp.dto.RoomInsertDTO;
import com.petbooking.bookingapp.dto.RoomReadOnlyDTO;
import com.petbooking.bookingapp.service.BookingService;
import com.petbooking.bookingapp.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Room", description = "Endpoints for managing rooms and availability")
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @Operation(
            summary = "[ADMIN] Create a new room",
            description = "Creates a new room based on the provided details."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomReadOnlyDTO> createRoom(@Valid @RequestBody RoomInsertDTO dto) {
        RoomReadOnlyDTO created = roomService.createRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "[PUBLIC] Get all rooms",
            description = "Retrieves a list of all available rooms"
    )
    @GetMapping
    public ResponseEntity<List<RoomReadOnlyDTO>>  getAllRooms() {
        List<RoomReadOnlyDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @Operation(
            summary = "[PUBLIC] Get room by ID",
            description = "Retrieves the details of a specific room by its unique ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomReadOnlyDTO> getRoomById(@PathVariable Long id) {
        RoomReadOnlyDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @Operation(
            summary = "[ADMIN] Delete a room",
            description = "Deletes a specific room by its unique ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "[ADMIN] Update room availability",
            description = "Updates the administrative availability status of a room for cases such as renovation or maintenance."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam("available") boolean available) {
        roomService.updateAvailability(id, available);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "[PUBLIC] Get disabled date ranges for a room",
            description = "Returns a list of date ranges during which the given room is unavailable (already booked). "
                    + "Useful for populating a calendar UI to disable check-in/check-out dates that overlap with existing bookings. "
                    + "Requires a date window [from, to] to limit the search."
                    + "**Date format must be in ISO format: yyyy-MM-dd (e.g. 2025-08-01)**."
    )
    @GetMapping("/{roomId}/calendar")
    public List<CalendarDisabledRangeDTO> getRoomCalendar(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        // Call service from(booking) to get disabled ranges for this room in given window
        return bookingService.getDisabledRangesForRoom(roomId, from, to);
    }
}
