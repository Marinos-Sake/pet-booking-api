package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.PetInsertDTO;
import com.petbooking.bookingapp.dto.PetReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal User user
            ) {
        PetReadOnlyDTO created = petService.createPet(dto, user.getId());
        return ResponseEntity.ok(created);
    }

    @GetMapping("/my")
    public ResponseEntity<List<PetReadOnlyDTO>> getAllPetsByUserId(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(petService.getAllPetsByUserId(user.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
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
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(petService.updatePet(id, dto, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        petService.deletePet(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
