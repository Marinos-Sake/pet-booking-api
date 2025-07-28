package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.RoomInsertDTO;
import com.petbooking.bookingapp.dto.RoomReadOnlyDTO;
import com.petbooking.bookingapp.entity.Room;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RoomMapper {

    public Room mapToRoomEntity(RoomInsertDTO dto, BigDecimal pricePerNight) {
        if (dto == null || pricePerNight == null) return null;

        Room room = new Room();
        room.setName(dto.getName());
        room.setType(dto.getType());
        room.setCapacity(dto.getCapacity());
        room.setDescription(dto.getDescription());
        room.setIsAvailable(dto.getIsAvailable());
        room.setPricePerNight(pricePerNight);

        return room;
    }

    public RoomReadOnlyDTO mapToReadOnlyDTO(Room room) {
        if (room == null) return null;

        RoomReadOnlyDTO dto = new RoomReadOnlyDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setType(room.getType().name());
        dto.setCapacity(room.getCapacity());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setDescription(room.getDescription());
        dto.setIsAvailable(room.getIsAvailable());

        return dto;
    }
}