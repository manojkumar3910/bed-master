package com.bedmaster.bed_service.service;

import com.bedmaster.bed_service.dto.request.BedAssignmentRequest;
import com.bedmaster.bed_service.dto.response.BedAssignmentResponse;
import com.bedmaster.bed_service.entity.BedAssignment;
import com.bedmaster.bed_service.entity.PatientStay;
import com.bedmaster.bed_service.enums.StayStatus;
import com.bedmaster.bed_service.exception.ResourceNotFoundException;
import com.bedmaster.bed_service.repository.BedAssignmentRepository;
import com.bedmaster.bed_service.repository.PatientStayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BedAssignmentService {

    private final BedAssignmentRepository bedAssignmentRepository;
    private final PatientStayRepository patientStayRepository;

    @Transactional
    public BedAssignmentResponse assignBed(BedAssignmentRequest request) {
        log.info("Assigning bed {} to patient/request {}", request.getBedId(), request.getRequestId());

        BedAssignment assignment = BedAssignment.builder()
                .requestId(request.getRequestId())
                .bedId(request.getBedId())
                .assignedBy(request.getAssignedBy())
                .status(request.getStatus())
                .assignedDate(LocalDateTime.now())
                .build();

        BedAssignment saved = bedAssignmentRepository.save(assignment);
        log.debug("BedAssignment saved with id {}", saved.getAssignmentId());

        PatientStay stay = PatientStay.builder()
                .PatientID(saved.getRequestId())
                .AdmissionDate(LocalDateTime.now())
                .CurrentBedID(saved.getBedId())
                .status(StayStatus.ADMITTED)
                .build();
        patientStayRepository.save(stay);
        log.info("Patient {} admitted to bed {}", saved.getRequestId(), saved.getBedId());

        return toResponse(saved);
    }

    public List<BedAssignmentResponse> getAssignments() {
        log.debug("Fetching all bed assignments");
        return bedAssignmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BedAssignmentResponse getAssignmentById(Long id) {
        log.debug("Fetching bed assignment with id {}", id);
        BedAssignment assignment = bedAssignmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Bed assignment not found with id {}", id);
                    return new ResourceNotFoundException("Bed assignment not found with id: " + id);
                });
        return toResponse(assignment);
    }

    private BedAssignmentResponse toResponse(BedAssignment a) {
        return BedAssignmentResponse.builder()
                .assignmentId(a.getAssignmentId())
                .requestId(a.getRequestId())
                .bedId(a.getBedId())
                .assignedBy(a.getAssignedBy())
                .assignedDate(a.getAssignedDate())
                .status(a.getStatus())
                .build();
    }
}
