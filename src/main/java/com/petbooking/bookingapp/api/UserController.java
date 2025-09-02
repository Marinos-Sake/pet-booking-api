package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.dto.UserUpdateDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User", description = "Endpoints for managing users and their profiles")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "[PUBLIC] Create a new user (with person profile)",
            description = "Creates a new user account and the associated Person. The request must include the required person details; the user will be linked to the newly created Person."
    )
    @PostMapping
    public ResponseEntity<UserReadOnlyDTO> createUser(@Valid @RequestBody UserInsertDTO dto) {
        UserReadOnlyDTO created = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "[ADMIN] Get user by ID",
            description = "Retrieves a user's details by their unique ID."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserReadOnlyDTO> getUserById(@PathVariable Long id) {
        UserReadOnlyDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "[ADMIN] Get all users (admin)",
            description = "Retrieves a paginated list of users. Query params: page (1-based), size/pageSize (max 100), sortBy (default 'id'), sortDirection (ASC|DESC, default DESC)."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserReadOnlyDTO>> getAllUsers(@ModelAttribute GenericFilters filters) {
        return ResponseEntity.ok(userService.getAllForAdmin(filters));
    }

    @Operation(
            summary = "Update my profile",
            description = "Updates the profile information of the currently authenticated user."
    )
    @PatchMapping("/me")
    public ResponseEntity<UserReadOnlyDTO> updateMe(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserUpdateDTO dto
    ) {
        return ResponseEntity.ok(userService.updateMyProfile(user, dto));
    }


    @Operation(
            summary = "Delete my account",
            description = "Deletes the currently authenticated user's account permanently."
    )
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal User user) {
        userService.deleteMe(user.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get my profile",
            description = "Retrieves the profile details of the currently authenticated user."
    )
    @GetMapping("/me")
    public ResponseEntity<UserReadOnlyDTO> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getMyProfile(user));
    }


}
