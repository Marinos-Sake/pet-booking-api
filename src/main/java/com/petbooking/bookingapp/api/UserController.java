package com.petbooking.bookingapp.api;


import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.UserMapper;
import com.petbooking.bookingapp.security.SecurityUtil;
import com.petbooking.bookingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserReadOnlyDTO> getUserById(@PathVariable Long id) {
        UserReadOnlyDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserReadOnlyDTO>> getAllUsers() {
        List<UserReadOnlyDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserReadOnlyDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserInsertDTO dto) {
        UserReadOnlyDTO updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserReadOnlyDTO> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.mapToReadOnlyDTO(user));
    }


}
