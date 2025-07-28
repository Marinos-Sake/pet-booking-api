package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.ReviewInsertDTO;
import com.petbooking.bookingapp.dto.ReviewReadOnlyDTO;
import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.Review;
import com.petbooking.bookingapp.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReviewMapper {

    public Review mapToReviewEntity(ReviewInsertDTO dto, User user, Booking booking) {

        if (dto == null || user == null || booking == null) return null;

        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setUser(user);
        review.setBooking(booking);
        review.setReviewDate(LocalDate.now());

        return review;
    }

    public ReviewReadOnlyDTO mapToReadOnlyDTO(Review review) {

        if (review == null) return null;

        ReviewReadOnlyDTO dto = new ReviewReadOnlyDTO();

        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewDate(review.getReviewDate());

        if (review.getUser() != null) {
            dto.setUsername(review.getUser().getUsername());
        }

        if (review.getBooking() != null) {
            dto.setBookingId(review.getBooking().getId());
        }

        return dto;
    }
}
