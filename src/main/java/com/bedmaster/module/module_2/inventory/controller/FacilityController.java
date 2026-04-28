package com.bedmaster.inventory.controller;

import com.bedmaster.inventory.dto.FacilityRequestDTO;
import com.bedmaster.inventory.dto.FacilityResponseDTO;
import com.bedmaster.inventory.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Facility APIs",
        description = "Operations related to hospital facilities"
)
@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @Operation(
            summary = "Get all facilities",
            description = "Returns the list of all hospital facilities"
    )
    @GetMapping
    public List<FacilityResponseDTO> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @Operation(
            summary = "Create a new facility",
            description = "Creates a hospital facility with name, campus, and status"
    )
    @PostMapping
    public FacilityResponseDTO createFacility(
            @Valid @RequestBody FacilityRequestDTO dto) {
        return facilityService.saveFacility(dto);
    }
}