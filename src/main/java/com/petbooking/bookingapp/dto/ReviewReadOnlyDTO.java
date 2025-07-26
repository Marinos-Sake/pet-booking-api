package com.petbooking.bookingapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReviewReadOnlyDTO {

    private Long id;
    private String comment;
    private Integer rating;
    private LocalDate reviewDate;

    private String username; //from user

    private Long bookingId;
}