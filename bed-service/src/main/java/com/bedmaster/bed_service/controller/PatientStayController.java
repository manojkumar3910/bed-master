package com.bedmaster.bed_service.controller;

import com.bedmaster.bed_service.dto.response.BedStatusResponse;
import com.bedmaster.bed_service.dto.response.PatientStayResponse;
import com.bedmaster.bed_service.service.PatientStayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/patients/stays")
@RequiredArgsConstructor
@Tag(name = "Patient Stay", description = "APIs for managing patient stays, discharges, and bed status")
public class PatientStayController {

    private final PatientStayService patientStayService;

    @GetMapping
    @Operation(summary = "Get all patient stays", description = "Returns all patient stay records")
    public ResponseEntity<List<PatientStayResponse>> getAllPatientStays() {
        return ResponseEntity.ok(patientStayService.getAllPatientStays());
    }

    @GetMapping("/{stayId}")
    @Operation(summary = "Get patient stay by ID")
    public ResponseEntity<PatientStayResponse> getPatientStay(
            @Parameter(description = "Patient stay ID") @PathVariable Long stayId) {
        return ResponseEntity.ok(patientStayService.getPatientStay(stayId));
    }

    @PutMapping("/{stayId}/discharge")
    @Operation(
            summary = "Discharge a patient",
            description = "Marks the patient stay as DISCHARGED and frees the bed for new admissions"
    )
    public ResponseEntity<PatientStayResponse> dischargePatient(
            @Parameter(description = "Patient stay ID") @PathVariable Long stayId) {
        log.info("PUT /api/patients/stays/{}/discharge", stayId);
        return ResponseEntity.ok(patientStayService.dischargePatient(stayId));
    }

    @GetMapping("/bed/{bedId}/status")
    @Operation(
            summary = "Get bed status",
            description = "Returns the current occupancy status of a bed: AVAILABLE, ADMITTED, or TRANSFERRED"
    )
    public ResponseEntity<BedStatusResponse> getBedStatus(
            @Parameter(description = "Bed ID") @PathVariable Long bedId) {
        return ResponseEntity.ok(patientStayService.getBedStatus(bedId));
    }
}
