package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppAccessDeniedException;
import com.petbooking.bookingapp.core.exception.AppObjectAlreadyExistsException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.dto.ReviewInsertDTO;
import com.petbooking.bookingapp.dto.ReviewReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Review;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.ReviewMapper;
import com.petbooking.bookingapp.repository.BookingRepository;
import com.petbooking.bookingapp.repository.ReviewRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ReviewMapper reviewMapper;

    public ReviewReadOnlyDTO createReview(ReviewInsertDTO dto, Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppObjectNotFoundException("REV_BOOK", "Booking not found"));

        if (!booking.getUser().getId().equals(userId)) {
            throw new SecurityException("You can only review your own bookings.");
        }

        if (!booking.getUser().getId().equals(userId)) {
            throw new AppAccessDeniedException(
                    "REV_UNAUTHORIZED",
                    "You are not allowed to review this booking.");
        }

        if (reviewRepository.findByBookingId(bookingId).isPresent()) {
            throw new AppObjectAlreadyExistsException(
                    "REV_",
                    "A review for booking ID " + bookingId + " already exists.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "REV_USR",
                        "User with ID " + userId + " not found"));

        Review review = reviewMapper.mapToReviewEntity(dto, user, booking);
        Review saved = reviewRepository.save(review);

        return reviewMapper.mapToReadOnlyDTO(saved);
    }

    public List<ReviewReadOnlyDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::mapToReadOnlyDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewReadOnlyDTO> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(reviewMapper::mapToReadOnlyDTO)
                .collect(Collectors.toList());
    }
}
