package com.bedmaster.inventory.controller;

import com.bedmaster.inventory.dto.BedReadinessResponseDTO;
import com.bedmaster.inventory.dto.BedRequestDTO;
import com.bedmaster.inventory.dto.BedResponseDTO;
import com.bedmaster.inventory.dto.BedStatusUpdateDTO;
import com.bedmaster.inventory.service.BedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Bed APIs",
        description = "Operations related to hospital beds and readiness"
)
@RestController
@RequestMapping("/api/beds")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @Operation(
            summary = "Update bed status",
            description = "Updates the status of a bed (AVAILABLE / OCCUPIED / OOS / CLEANING)"
    )
    @PatchMapping("/{bedId}/status")
    public BedResponseDTO updateBedStatus(
            @Parameter(description = "Bed ID", example = "300")
            @PathVariable Integer bedId,
            @Valid @RequestBody BedStatusUpdateDTO request) {

        return bedService.updateBedStatus(bedId, request.getStatus());
    }

    @Operation(
            summary = "Get beds by room",
            description = "Returns all beds under a given room"
    )
    @GetMapping
    public List<BedResponseDTO> getBedsByRoom(
            @Parameter(description = "Room ID", example = "200")
            @RequestParam Integer roomId) {

        return bedService.getBedsByRoom(roomId);
    }

    @Operation(
            summary = "Check bed readiness",
            description = "Returns whether a bed is ready for patient assignment"
    )
    @GetMapping("/{bedId}/readiness")
    public BedReadinessResponseDTO checkBedReadiness(
            @Parameter(description = "Bed ID", example = "300")
            @PathVariable Integer bedId) {

        return bedService.checkBedReadiness(bedId);
    }

    @Operation(
            summary = "Create a new bed",
            description = "Creates a bed under a room with bed type and status"
    )
    @PostMapping
    public BedResponseDTO createBed(@Valid @RequestBody BedRequestDTO dto) {
        return bedService.createBed(dto);
    }
}
