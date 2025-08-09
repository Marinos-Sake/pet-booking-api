package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Booking;
import com.petbooking.bookingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Returns all bookings associated with the given user.
     */
    List<Booking> findAllByUser(User user);

    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);


    /**
     * Checks if a room is already booked (overlapping dates) for the given period.
     * Used to prevent double bookings.
     */
    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    WHERE b.room.id = :roomId
    AND b.status IN ('PENDING', 'CONFIRMED', 'COMPLETED')
    AND b.checkInDate < :checkOutDate
    AND b.checkOutDate > :checkInDate
""")
    boolean existsByRoomIdAndDatesOverlap(@Param("roomId") Long roomId,
                                          @Param("checkInDate") LocalDate checkIn,
                                          @Param("checkOutDate") LocalDate checkOut);

}
