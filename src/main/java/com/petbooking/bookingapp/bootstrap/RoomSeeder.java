package com.petbooking.bookingapp.bootstrap;


import com.petbooking.bookingapp.core.enums.RoomType;
import com.petbooking.bookingapp.entity.Room;
import com.petbooking.bookingapp.repository.RoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomSeeder {

    private final RoomRepository roomRepository;

    @PostConstruct
    public void seedRoom() {
        if (roomRepository.count() == 0) {
            List<Room> rooms = List.of(
                    new Room(null, "Small Room", "For one pet ideal for cats or small dogs", 1, RoomType.SMALL, BigDecimal.valueOf(25.00), true, new ArrayList<>()),
                    new Room(null, "Medium Room", "Fits two pets good for siblings or medium dogs", 2, RoomType.MEDIUM, BigDecimal.valueOf(40.00), true, new ArrayList<>()),
                    new Room(null, "Large Room", "Spacious and comfortable - for large dogs or group", 3, RoomType.LARGE, BigDecimal.valueOf(60.00), true, new ArrayList<>())
            );

            roomRepository.saveAll(rooms);
            System.out.println("Default rooms seeded successfully.");
        } else {
            System.out.println("Rooms already exist skipping seeding.");
        }
    }
}
