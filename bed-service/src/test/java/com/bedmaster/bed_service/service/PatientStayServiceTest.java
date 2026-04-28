package com.bedmaster.bed_service.service;

import com.bedmaster.bed_service.dto.response.BedStatusResponse;
import com.bedmaster.bed_service.dto.response.PatientStayResponse;
import com.bedmaster.bed_service.entity.PatientStay;
import com.bedmaster.bed_service.enums.StayStatus;
import com.bedmaster.bed_service.exception.BadRequestException;
import com.bedmaster.bed_service.exception.ResourceNotFoundException;
import com.bedmaster.bed_service.repository.PatientStayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class PatientStayServiceTest {

    @Mock
    private PatientStayRepository patientStayRepository;

    @InjectMocks
    private PatientStayService patientStayService;

    private PatientStay admittedStay;

    @BeforeEach
    void setUp() {
        admittedStay = PatientStay.builder()
                .StayID(1L)
                .PatientID(1001L)
                .AdmissionDate(LocalDateTime.now())
                .CurrentBedID(201L)
                .CurrentUnitID(10L)
                .status(StayStatus.ADMITTED)
                .build();
    }

    // ─── getAllPatientStays ───────────────────────────────────────────────────

    @Test
    @DisplayName("getAllPatientStays - should return mapped list")
    void getAllPatientStays_returnsList() {
        when(patientStayRepository.findAll()).thenReturn(List.of(admittedStay));

        List<PatientStayResponse> result = patientStayService.getAllPatientStays();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStayId()).isEqualTo(1L);
        assertThat(result.get(0).getPatientId()).isEqualTo(1001L);
        assertThat(result.get(0).getStatus()).isEqualTo(StayStatus.ADMITTED);
    }

    // ─── getPatientStay ───────────────────────────────────────────────────────

    @Test
    @DisplayName("getPatientStay - should return response when found")
    void getPatientStay_found() {
        when(patientStayRepository.findById(1L)).thenReturn(Optional.of(admittedStay));

        PatientStayResponse response = patientStayService.getPatientStay(1L);

        assertThat(response.getStayId()).isEqualTo(1L);
        assertThat(response.getCurrentBedId()).isEqualTo(201L);
    }

    @Test
    @DisplayName("getPatientStay - should throw ResourceNotFoundException when not found")
    void getPatientStay_notFound() {
        when(patientStayRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientStayService.getPatientStay(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── dischargePatient ─────────────────────────────────────────────────────

    @Test
    @DisplayName("dischargePatient - should update status to DISCHARGED and return response")
    void dischargePatient_success() {
        PatientStay saved = PatientStay.builder()
                .StayID(1L).PatientID(1001L)
                .AdmissionDate(admittedStay.getAdmissionDate())
                .CurrentBedID(201L).CurrentUnitID(10L)
                .status(StayStatus.DISCHARGED).build();

        when(patientStayRepository.findById(1L)).thenReturn(Optional.of(admittedStay));
        when(patientStayRepository.save(any(PatientStay.class))).thenReturn(saved);

        PatientStayResponse response = patientStayService.dischargePatient(1L);

        assertThat(response.getStatus()).isEqualTo(StayStatus.DISCHARGED);
        verify(patientStayRepository).save(admittedStay);
    }

    @Test
    @DisplayName("dischargePatient - should throw BadRequestException if already discharged")
    void dischargePatient_alreadyDischarged() {
        admittedStay.setStatus(StayStatus.DISCHARGED);
        when(patientStayRepository.findById(1L)).thenReturn(Optional.of(admittedStay));

        assertThatThrownBy(() -> patientStayService.dischargePatient(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("already discharged");

        verify(patientStayRepository, never()).save(any());
    }

    @Test
    @DisplayName("dischargePatient - should throw ResourceNotFoundException when stay not found")
    void dischargePatient_stayNotFound() {
        when(patientStayRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientStayService.dischargePatient(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── getBedStatus ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("getBedStatus - should return AVAILABLE when no patient in bed")
    void getBedStatus_available_noPatient() {
        when(patientStayRepository.findByCurrentBedID(201L)).thenReturn(null);

        BedStatusResponse response = patientStayService.getBedStatus(201L);

        assertThat(response.getBedId()).isEqualTo(201L);
        assertThat(response.getStatus()).isEqualTo("AVAILABLE");
        assertThat(response.getPatientId()).isNull();
    }

    @Test
    @DisplayName("getBedStatus - should return AVAILABLE when patient is discharged")
    void getBedStatus_available_patientDischarged() {
        admittedStay.setStatus(StayStatus.DISCHARGED);
        when(patientStayRepository.findByCurrentBedID(201L)).thenReturn(admittedStay);

        BedStatusResponse response = patientStayService.getBedStatus(201L);

        assertThat(response.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test
    @DisplayName("getBedStatus - should return ADMITTED with patientId when occupied")
    void getBedStatus_occupied() {
        when(patientStayRepository.findByCurrentBedID(201L)).thenReturn(admittedStay);

        BedStatusResponse response = patientStayService.getBedStatus(201L);

        assertThat(response.getBedId()).isEqualTo(201L);
        assertThat(response.getStatus()).isEqualTo("ADMITTED");
        assertThat(response.getPatientId()).isEqualTo(1001L);
    }
}
