package com.petbooking.bookingapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PaymentReadOnlyDTO {

    private Long id;

    private BigDecimal amount;

    private LocalDate paymentDate;

    private Long bookingId;

    private String userFullName;  // από το booking.getUser().getPerson().getFullName()
    private String petName;       // από booking.getPet().getName()
    private String roomName;      // από booking.getRoom().getName()

}
