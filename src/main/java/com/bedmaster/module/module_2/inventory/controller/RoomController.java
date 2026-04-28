package com.bedmaster.inventory.controller;

import com.bedmaster.inventory.dto.RoomRequestDTO;
import com.bedmaster.inventory.dto.RoomResponseDTO;
import com.bedmaster.inventory.dto.RoomStatusUpdateDTO;
import com.bedmaster.inventory.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Room APIs",
        description = "Operations related to rooms within hospital units"
)
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(
            summary = "Update room status",
            description = "Updates the status of a room (AVAILABLE / OOS)"
    )
    @PatchMapping("/{roomId}/status")
    public RoomResponseDTO updateRoomStatus(
            @Parameter(description = "Room ID", example = "200")
            @PathVariable Integer roomId,
            @Valid @RequestBody RoomStatusUpdateDTO request) {

        return roomService.updateRoomStatus(roomId, request.getStatus());
    }

    @Operation(
            summary = "Get rooms by unit",
            description = "Returns all rooms under a given unit"
    )
    @GetMapping
    public List<RoomResponseDTO> getRoomsByUnit(
            @Parameter(description = "Unit ID", example = "100")
            @RequestParam Integer unitId) {

        return roomService.getRoomsByUnit(unitId);
    }

    @Operation(
            summary = "Create a new room",
            description = "Creates a room under a unit with room number, gender, and status"
    )
    @PostMapping
    public RoomResponseDTO createRoom(@Valid @RequestBody RoomRequestDTO dto) {
        return roomService.createRoom(dto);
    }
}
