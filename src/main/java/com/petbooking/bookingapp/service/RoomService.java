package com.petbooking.bookingapp.service;

import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.dto.RoomInsertDTO;
import com.petbooking.bookingapp.dto.RoomReadOnlyDTO;
import com.petbooking.bookingapp.entity.Room;
import com.petbooking.bookingapp.mapper.RoomMapper;
import com.petbooking.bookingapp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomReadOnlyDTO createRoom(RoomInsertDTO dto) {
        Room room = roomMapper.mapToRoomEntity(dto);
        Room saved = roomRepository.save(room);
        return roomMapper.mapToReadOnlyDTO(saved);
    }

    public List<RoomReadOnlyDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::mapToReadOnlyDTO)
                .collect(Collectors.toList());
    }

    public RoomReadOnlyDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "Room_",
                        "Room not found with ID: " + id
                ));
        return roomMapper.mapToReadOnlyDTO(room);
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new AppObjectNotFoundException(
                    "Room_",
                    "Room not found with ID: " + id
            );
        }
        roomRepository.deleteById(id);
    }

    @Transactional
    public void updateAvailability(Long id, boolean isAvailable) {
        if (!roomRepository.existsById(id)) {
            throw new AppObjectNotFoundException("Room_", "Room not found with ID: " + id);
        }
        roomRepository.updateAvailability(id, isAvailable);
    }
}
