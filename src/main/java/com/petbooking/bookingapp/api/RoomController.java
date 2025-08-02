package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.RoomInsertDTO;
import com.petbooking.bookingapp.dto.RoomReadOnlyDTO;
import com.petbooking.bookingapp.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomReadOnlyDTO> createRoom(@Valid @RequestBody RoomInsertDTO dto) {
        RoomReadOnlyDTO created = roomService.createRoom(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<RoomReadOnlyDTO>>  getAllRooms() {
        List<RoomReadOnlyDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomReadOnlyDTO> getRoomById(@PathVariable Long id) {
        RoomReadOnlyDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam("available") boolean available) {
        roomService.updateAvailability(id, available);
        return ResponseEntity.noContent().build();
    }
}
