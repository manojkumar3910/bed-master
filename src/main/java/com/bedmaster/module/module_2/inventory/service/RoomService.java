package com.bedmaster.inventory.service;

import com.bedmaster.inventory.dto.RoomRequestDTO;
import com.bedmaster.inventory.dto.RoomResponseDTO;
import com.bedmaster.inventory.entity.Room;

import com.bedmaster.inventory.entity.Unit;
import com.bedmaster.inventory.enums.UnitStatus;
import com.bedmaster.inventory.exception.UnitNotFoundException;
import com.bedmaster.inventory.repository.UnitRepository;

import com.bedmaster.inventory.enums.RoomGender;
import com.bedmaster.inventory.enums.RoomStatus;
import com.bedmaster.inventory.exception.InvalidStatusTransitionException;
import com.bedmaster.inventory.exception.RoomNotFoundException;
import com.bedmaster.inventory.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UnitRepository unitRepository;


    public RoomService(RoomRepository roomRepository,
                       UnitRepository unitRepository
                       ) {
        this.roomRepository = roomRepository;
        this.unitRepository = unitRepository;
    }


    // ✅ GET – Rooms by Unit
    public List<RoomResponseDTO> getRoomsByUnit(Integer unitId) {
        return roomRepository.findByUnitID(unitId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ✅ PATCH – Update Room Status
    public RoomResponseDTO updateRoomStatus(Integer roomId, String status) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        Unit unit = unitRepository.findById(room.getUnitID())
                .orElseThrow(() -> new UnitNotFoundException(room.getUnitID()));

        if (unit.getStatus() == UnitStatus.INACTIVE) {
            throw new InvalidStatusTransitionException(
                    "Cannot update room status under inactive unit"
            );
        }

        String oldStatus = room.getStatus().name();

        try {
            room.setStatus(
                    RoomStatus.valueOf(status.toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException(
                    "Invalid room status: " + status
            );
        }

        Room savedRoom = roomRepository.save(room);


        return convertToDto(savedRoom);
      }


    // ✅ POST – Create Room
    public RoomResponseDTO createRoom(RoomRequestDTO dto) {

        Unit unit = unitRepository.findById(dto.getUnitID())
                .orElseThrow(() ->
                        new UnitNotFoundException(dto.getUnitID())
                );

        // ✅ CROSS‑LEVEL RULE
        if (unit.getStatus() == UnitStatus.INACTIVE) {
            throw new InvalidStatusTransitionException(
                    "Cannot create room under inactive unit"
            );
        }

        Room room = new Room();
        room.setUnitID(dto.getUnitID());
        room.setRoomNumber(dto.getRoomNumber());

        try {
            room.setGender(
                    RoomGender.valueOf(dto.getGender().toUpperCase())
            );
            room.setStatus(
                    RoomStatus.valueOf(dto.getStatus().toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException(
                    "Invalid room gender or status"
            );
        }

        return convertToDto(roomRepository.save(room));
    }

    // ✅ Entity → DTO
    private RoomResponseDTO convertToDto(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setRoomID(room.getRoomID());
        dto.setUnitID(room.getUnitID());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setGender(room.getGender().name());
        dto.setStatus(room.getStatus().name());
        return dto;
    }
}