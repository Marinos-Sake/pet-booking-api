package com.petbooking.bookingapp.mapper;

import com.petbooking.bookingapp.dto.RoomInsertDTO;
import com.petbooking.bookingapp.dto.RoomReadOnlyDTO;
import com.petbooking.bookingapp.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room mapToRoomEntity(RoomInsertDTO dto) {
        if (dto == null) return null;

        Room room = new Room();
        room.setName(dto.getName());
        room.setType(dto.getType());
        room.setCapacity(dto.getCapacity());
        room.setDescription(dto.getDescription());
        room.setPricePerNight(dto.getPricePerNight());
        room.setIsAvailable(dto.getIsAvailable() != null ? dto.getIsAvailable() : true);


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