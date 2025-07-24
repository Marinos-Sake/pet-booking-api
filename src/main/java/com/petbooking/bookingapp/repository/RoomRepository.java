package com.petbooking.bookingapp.repository;

import com.petbooking.bookingapp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
