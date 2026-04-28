package com.bedmaster.inventory.controller;

import com.bedmaster.inventory.dto.UnitRequestDTO;
import com.bedmaster.inventory.dto.UnitResponseDTO;
import com.bedmaster.inventory.dto.UnitStatusUpdateDTO;
import com.bedmaster.inventory.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Unit APIs",
        description = "Operations related to hospital units within a facility"
)
@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @Operation(
            summary = "Update unit status",
            description = "Updates the status of a unit (ACTIVE / INACTIVE / MAINTENANCE)"
    )
    @PatchMapping("/{unitId}/status")
    public UnitResponseDTO updateUnitStatus(
            @Parameter(description = "Unit ID", example = "100")
            @PathVariable Integer unitId,
            @Valid @RequestBody UnitStatusUpdateDTO request) {

        return unitService.updateUnitStatus(unitId, request.getStatus());
    }

    @Operation(
            summary = "Get units by facility",
            description = "Returns all units under a given facility"
    )
    @GetMapping
    public List<UnitResponseDTO> getUnitsByFacility(
            @Parameter(description = "Facility ID", example = "10")
            @RequestParam Integer facilityId) {

        return unitService.getUnitsByFacility(facilityId);
    }

    @Operation(
            summary = "Create a new unit",
            description = "Creates a unit under a facility with specialty, capacity, and status"
    )
    @PostMapping
    public UnitResponseDTO createUnit(@Valid @RequestBody UnitRequestDTO dto) {
        return unitService.createUnit(dto);
    }
}