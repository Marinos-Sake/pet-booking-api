package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.PetInsertDTO;
import com.petbooking.bookingapp.dto.PetReadOnlyDTO;
import com.petbooking.bookingapp.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetReadOnlyDTO> createPet(
            @RequestBody @Valid PetInsertDTO dto,
            @RequestParam Long userId
            ) {
        PetReadOnlyDTO created = petService.createPet(dto, userId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetReadOnlyDTO>> getAllPetsByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(petService.getAllPetsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetReadOnlyDTO> getPetById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetReadOnlyDTO> updatePet(
            @PathVariable Long id,
            @RequestBody @Valid PetInsertDTO dto,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(petService.updatePet(id, dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        petService.deletePet(id, userId);
        return ResponseEntity.noContent().build();
    }
}
