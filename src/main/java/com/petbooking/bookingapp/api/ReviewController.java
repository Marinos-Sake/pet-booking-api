package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.ReviewInsertDTO;
import com.petbooking.bookingapp.dto.ReviewReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ReviewReadOnlyDTO> createReview(
            @Valid @RequestBody ReviewInsertDTO dto,
            @RequestParam Long bookingId,
            @AuthenticationPrincipal User user
    ) {
        ReviewReadOnlyDTO created = reviewService.createReview(dto, bookingId, user.getId());
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<ReviewReadOnlyDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public ResponseEntity<List<ReviewReadOnlyDTO>> getMyReviews(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(user.getId()));
    }
}
