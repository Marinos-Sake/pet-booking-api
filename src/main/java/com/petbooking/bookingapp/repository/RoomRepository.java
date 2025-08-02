package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Query("UPDATE Room r SET r.isAvailable = :available WHERE r.id = :id")
    void updateAvailability(@Param("id") Long id, @Param("available") boolean available);

}
