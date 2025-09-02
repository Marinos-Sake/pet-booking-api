package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.ReviewInsertDTO;
import com.petbooking.bookingapp.dto.ReviewReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review", description = "Endpoints for managing users reviews")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @Operation(
            summary = "Create a new review",
            description = "Creates a new review for a specific booking by the authenticated user Access restricted to USER role"
    )
    @PostMapping
    public ResponseEntity<ReviewReadOnlyDTO> createReview(
            @Valid @RequestBody ReviewInsertDTO dto,
            @RequestParam Long bookingId,
            @AuthenticationPrincipal User user
    ) {
        ReviewReadOnlyDTO created = reviewService.createReview(dto, bookingId, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @Operation(
            summary = "[PUBLIC] Get all reviews",
            description = "Retrieves a list of all reviews in the system"
    )
    @GetMapping
    public ResponseEntity<List<ReviewReadOnlyDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }


    @Operation(
            summary = "Get my reviews",
            description = "Retrieves all reviews created by the currently authenticated user Accessible only to USER role"
    )
    @GetMapping("/my")
    public ResponseEntity<List<ReviewReadOnlyDTO>> getMyReviews(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(user.getId()));
    }
}
