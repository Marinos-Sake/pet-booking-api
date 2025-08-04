package com.petbooking.bookingapp.api;

import com.petbooking.bookingapp.dto.ReviewInsertDTO;
import com.petbooking.bookingapp.dto.ReviewReadOnlyDTO;
import com.petbooking.bookingapp.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Δημιουργία review
    @PostMapping
    public ResponseEntity<ReviewReadOnlyDTO> createReview(
            @Valid @RequestBody ReviewInsertDTO dto,
            @RequestParam Long bookingId,
            @RequestParam Long userId // ToDo authentication
    ) {
        ReviewReadOnlyDTO created = reviewService.createReview(dto, bookingId, userId);
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<ReviewReadOnlyDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }


    @GetMapping("/my") //ToDo authentication
    public ResponseEntity<List<ReviewReadOnlyDTO>> getMyReviews(@RequestParam Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }
}
