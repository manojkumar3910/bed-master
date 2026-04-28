package com.bedmaster.bed_service.controller;

import com.bedmaster.bed_service.dto.request.BedAssignmentRequest;
import com.bedmaster.bed_service.dto.response.BedAssignmentResponse;
import com.bedmaster.bed_service.service.BedAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/beds")
@RequiredArgsConstructor
@Tag(name = "Bed Assignment", description = "APIs for assigning hospital beds to patients and tracking occupancy")
public class BedAssignmentController {

    private final BedAssignmentService bedAssignmentService;

    @PostMapping("/assign")
    @Operation(
            summary = "Assign a bed to a patient",
            description = "Creates a new bed assignment and automatically records the patient's admission"
    )
    public ResponseEntity<BedAssignmentResponse> assignBed(@Valid @RequestBody BedAssignmentRequest request) {
        log.info("POST /api/beds/assign - bedId={}, requestId={}", request.getBedId(), request.getRequestId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bedAssignmentService.assignBed(request));
    }

    @GetMapping
    @Operation(summary = "Get all bed assignments", description = "Returns a list of all bed assignments")
    public ResponseEntity<List<BedAssignmentResponse>> getAssignments() {
        return ResponseEntity.ok(bedAssignmentService.getAssignments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bed assignment by ID")
    public ResponseEntity<BedAssignmentResponse> getAssignmentById(
            @Parameter(description = "Bed assignment ID") @PathVariable Long id) {
        return ResponseEntity.ok(bedAssignmentService.getAssignmentById(id));
    }
}
