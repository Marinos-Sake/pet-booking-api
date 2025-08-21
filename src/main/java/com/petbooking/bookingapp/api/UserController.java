package com.petbooking.bookingapp.api;


import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.UserMapper;
import com.petbooking.bookingapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserReadOnlyDTO> createUser(@Valid @RequestBody UserInsertDTO dto) {
        UserReadOnlyDTO created = userService.createUser(dto);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserReadOnlyDTO> getUserById(@PathVariable Long id) {
        UserReadOnlyDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserReadOnlyDTO>> getAllUsers(@ModelAttribute GenericFilters filters) {
        return ResponseEntity.ok(userService.getAllForAdmin(filters));
    }

    @PutMapping("/me")
    public ResponseEntity<UserReadOnlyDTO> updateMyProfile(
            @Valid @RequestBody UserInsertDTO dto,
            @AuthenticationPrincipal User user) {

        UserReadOnlyDTO updated = userService.updateUser(user.getId(), dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserReadOnlyDTO> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.mapToReadOnlyDTO(user));
    }


}
