package com.bedmaster.inventory.service;

import com.bedmaster.inventory.dto.BedReadinessResponseDTO;
import com.bedmaster.inventory.dto.BedRequestDTO;
import com.bedmaster.inventory.dto.BedResponseDTO;
import com.bedmaster.inventory.entity.Bed;
import com.bedmaster.inventory.entity.Room;
import com.bedmaster.inventory.enums.BedStatus;
import com.bedmaster.inventory.enums.BedType;
import com.bedmaster.inventory.enums.RoomStatus;
import com.bedmaster.inventory.exception.BedNotFoundException;
import com.bedmaster.inventory.exception.InvalidStatusTransitionException;
import com.bedmaster.inventory.exception.RoomNotFoundException;
import com.bedmaster.inventory.repository.BedRepository;
import com.bedmaster.inventory.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BedService(BedRepository bedRepository,
                      RoomRepository roomRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }
    private List<String> extractEnabledAttributes(String attributesJson) {
        try {
            Map<String, Boolean> map =
                    objectMapper.readValue(attributesJson, Map.class);

            return map.entrySet()
                    .stream()
                    .filter(entry -> Boolean.TRUE.equals(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return List.of();
        }
    }

    public List<BedResponseDTO> getBedsByRoom(Integer roomId) {
        return bedRepository.findByRoomID(roomId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BedResponseDTO updateBedStatus(Integer bedId, String status) {

        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new BedNotFoundException(bedId));

        Room room = roomRepository.findById(bed.getRoomID())
                .orElseThrow(() -> new RoomNotFoundException(bed.getRoomID()));

        if (room.getStatus() == RoomStatus.OOS) {
            throw new InvalidStatusTransitionException(
                    "Cannot update bed status under OOS room"
            );
        }

        try {
            bed.setStatus(BedStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException(
                    "Invalid bed status: " + status
            );
        }

        return convertToDto(bedRepository.save(bed));
    }

    public BedReadinessResponseDTO checkBedReadiness(Integer bedId) {

        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new BedNotFoundException(bedId));

        Room room = roomRepository.findById(bed.getRoomID())
                .orElseThrow(() -> new RoomNotFoundException(bed.getRoomID()));

        if (room.getStatus() == RoomStatus.OOS) {
            return new BedReadinessResponseDTO(false, "ROOM_OOS");
        }

        if (bed.getStatus() != BedStatus.AVAILABLE) {
            return new BedReadinessResponseDTO(false, "BED_NOT_AVAILABLE");
        }

        return new BedReadinessResponseDTO(true, null);
    }

    public BedResponseDTO createBed(BedRequestDTO dto) {

        Room room = roomRepository.findById(dto.getRoomID())
                .orElseThrow(() -> new RoomNotFoundException(dto.getRoomID()));

        if (room.getStatus() == RoomStatus.OOS) {
            throw new InvalidStatusTransitionException(
                    "Cannot create bed under OOS room"
            );
        }

        Bed bed = new Bed();
        bed.setRoomID(dto.getRoomID());
        bed.setBedNumber(dto.getBedNumber());

        try {
            bed.setBedType(BedType.valueOf(dto.getBedType().toUpperCase()));
            bed.setStatus(BedStatus.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException(
                    "Invalid bed type or status"
            );
        }

        // ✅ SAVE ATTRIBUTES JSON
        if (dto.getAttributes() != null) {
            try {
                bed.setAttributesJson(
                        objectMapper.writeValueAsString(dto.getAttributes())
                );
            } catch (Exception e) {
                throw new RuntimeException("Invalid attributes JSON", e);
            }
        }

        return convertToDto(bedRepository.save(bed));
    }

    private BedResponseDTO convertToDto(Bed bed) {

        BedResponseDTO dto = new BedResponseDTO();

        // ✅ FIXED naming
        dto.setBedId(bed.getBedID());

        dto.setBedNumber(bed.getBedNumber());
        dto.setBedType(bed.getBedType().name());
        dto.setStatus(bed.getStatus().name());

        // ✅ Boolean JSON → List<String> (only true values)
        if (bed.getAttributesJson() != null) {
            dto.setAttributes(
                    extractEnabledAttributes(bed.getAttributesJson())
            );
        }

        return dto;
    }
}