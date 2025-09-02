package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.PetInsertDTO;
import com.petbooking.bookingapp.dto.PetReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pet", description = "Endpoints for managing pets of users")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @Operation(
            summary = "Create a new pet",
            description = "Creates a new pet for the authenticated user based on the provided details"
    )
    @PostMapping
    public ResponseEntity<PetReadOnlyDTO> createPet(
            @RequestBody @Valid PetInsertDTO dto,
            @AuthenticationPrincipal User user
            ) {
        PetReadOnlyDTO created = petService.createPet(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Get my pets",
            description = "Retrieves all pets that belong to the currently authenticated user"
    )
    @GetMapping("/my")
    public ResponseEntity<List<PetReadOnlyDTO>> getMyPets(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(petService.getAllPetsByUserId(user));
    }

    @Operation(
            summary = "[ADMIN] Get pet by ID",
            description = "Retrieves the details of a specific pet by its unique ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PetReadOnlyDTO> getPetById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(petService.getPetById(id));
    }


    @Operation(
            summary = "Delete a pet",
            description = "Deletes a specific pet that belongs to the authenticated user"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        petService.deletePet(id, user);
        return ResponseEntity.noContent().build();
    }
}
