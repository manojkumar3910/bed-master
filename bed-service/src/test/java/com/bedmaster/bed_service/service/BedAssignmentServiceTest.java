package com.bedmaster.bed_service.service;

import com.bedmaster.bed_service.dto.request.BedAssignmentRequest;
import com.bedmaster.bed_service.dto.response.BedAssignmentResponse;
import com.bedmaster.bed_service.entity.BedAssignment;
import com.bedmaster.bed_service.enums.AssignmentStatus;
import com.bedmaster.bed_service.exception.ResourceNotFoundException;
import com.bedmaster.bed_service.repository.BedAssignmentRepository;
import com.bedmaster.bed_service.repository.PatientStayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BedAssignmentServiceTest {

    @Mock
    private BedAssignmentRepository bedAssignmentRepository;

    @Mock
    private PatientStayRepository patientStayRepository;

    @InjectMocks
    private BedAssignmentService bedAssignmentService;

    private BedAssignment sampleAssignment;

    @BeforeEach
    void setUp() {
        sampleAssignment = BedAssignment.builder()
                .assignmentId(1L)
                .requestId(1001L)
                .bedId(201L)
                .assignedBy("Dr. Smith")
                .assignedDate(LocalDateTime.now())
                .status(AssignmentStatus.CONFIRMED)
                .build();
    }

    // ─── assignBed ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("assignBed - should save assignment and patient stay, return response")
    void assignBed_success() {
        BedAssignmentRequest request = new BedAssignmentRequest();
        request.setRequestId(1001L);
        request.setBedId(201L);
        request.setAssignedBy("Dr. Smith");
        request.setStatus(AssignmentStatus.CONFIRMED);

        when(bedAssignmentRepository.save(any(BedAssignment.class))).thenReturn(sampleAssignment);

        BedAssignmentResponse response = bedAssignmentService.assignBed(request);

        assertThat(response.getAssignmentId()).isEqualTo(1L);
        assertThat(response.getRequestId()).isEqualTo(1001L);
        assertThat(response.getBedId()).isEqualTo(201L);
        assertThat(response.getAssignedBy()).isEqualTo("Dr. Smith");
        assertThat(response.getStatus()).isEqualTo(AssignmentStatus.CONFIRMED);

        verify(bedAssignmentRepository).save(any(BedAssignment.class));
        verify(patientStayRepository).save(any());
    }

    @Test
    @DisplayName("assignBed - should set assignedDate automatically")
    void assignBed_setsAssignedDateAutomatically() {
        BedAssignmentRequest request = new BedAssignmentRequest();
        request.setRequestId(1001L);
        request.setBedId(201L);
        request.setAssignedBy("Dr. Smith");
        request.setStatus(AssignmentStatus.CONFIRMED);

        when(bedAssignmentRepository.save(any(BedAssignment.class))).thenReturn(sampleAssignment);

        ArgumentCaptor<BedAssignment> captor = ArgumentCaptor.forClass(BedAssignment.class);
        bedAssignmentService.assignBed(request);

        verify(bedAssignmentRepository).save(captor.capture());
        assertThat(captor.getValue().getAssignedDate()).isNotNull();
    }

    // ─── getAssignments ───────────────────────────────────────────────────────

    @Test
    @DisplayName("getAssignments - should return all assignments as response list")
    void getAssignments_returnsList() {
        BedAssignment second = BedAssignment.builder()
                .assignmentId(2L).requestId(1002L).bedId(202L)
                .assignedBy("Nurse Lee").assignedDate(LocalDateTime.now())
                .status(AssignmentStatus.HELD).build();

        when(bedAssignmentRepository.findAll()).thenReturn(List.of(sampleAssignment, second));

        List<BedAssignmentResponse> result = bedAssignmentService.getAssignments();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getAssignmentId()).isEqualTo(1L);
        assertThat(result.get(1).getAssignmentId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("getAssignments - should return empty list when no assignments exist")
    void getAssignments_emptyList() {
        when(bedAssignmentRepository.findAll()).thenReturn(List.of());

        List<BedAssignmentResponse> result = bedAssignmentService.getAssignments();

        assertThat(result).isEmpty();
    }

    // ─── getAssignmentById ────────────────────────────────────────────────────

    @Test
    @DisplayName("getAssignmentById - should return response when found")
    void getAssignmentById_found() {
        when(bedAssignmentRepository.findById(1L)).thenReturn(Optional.of(sampleAssignment));

        BedAssignmentResponse response = bedAssignmentService.getAssignmentById(1L);

        assertThat(response.getAssignmentId()).isEqualTo(1L);
        assertThat(response.getBedId()).isEqualTo(201L);
    }

    @Test
    @DisplayName("getAssignmentById - should throw ResourceNotFoundException when not found")
    void getAssignmentById_notFound() {
        when(bedAssignmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bedAssignmentService.getAssignmentById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
