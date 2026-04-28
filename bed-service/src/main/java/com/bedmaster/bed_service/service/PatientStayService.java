package com.bedmaster.bed_service.service;

import com.bedmaster.bed_service.dto.response.BedStatusResponse;
import com.bedmaster.bed_service.dto.response.PatientStayResponse;
import com.bedmaster.bed_service.entity.PatientStay;
import com.bedmaster.bed_service.enums.StayStatus;
import com.bedmaster.bed_service.exception.BadRequestException;
import com.bedmaster.bed_service.exception.ResourceNotFoundException;
import com.bedmaster.bed_service.repository.PatientStayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientStayService {

    private final PatientStayRepository patientStayRepository;

    public List<PatientStayResponse> getAllPatientStays() {
        log.debug("Fetching all patient stays");
        return patientStayRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PatientStayResponse getPatientStay(Long stayId) {
        log.debug("Fetching patient stay with id {}", stayId);
        return toResponse(findById(stayId));
    }

    @Transactional
    public PatientStayResponse dischargePatient(Long stayId) {
        log.info("Discharging patient with stay id {}", stayId);
        PatientStay stay = findById(stayId);
        if (stay.getStatus() == StayStatus.DISCHARGED) {
            log.warn("Attempted to discharge already-discharged patient with stay id {}", stayId);
            throw new BadRequestException("Patient with stay id " + stayId + " is already discharged");
        }
        stay.setStatus(StayStatus.DISCHARGED);
        PatientStayResponse response = toResponse(patientStayRepository.save(stay));
        log.info("Patient with stay id {} successfully discharged from bed {}", stayId, stay.getCurrentBedID());
        return response;
    }

    public BedStatusResponse getBedStatus(Long bedId) {
        log.debug("Checking status for bed {}", bedId);
        PatientStay stay = patientStayRepository.findByCurrentBedID(bedId);
        if (stay == null || stay.getStatus() == StayStatus.DISCHARGED) {
            log.debug("Bed {} is AVAILABLE", bedId);
            return BedStatusResponse.builder()
                    .bedId(bedId)
                    .status("AVAILABLE")
                    .build();
        }
        log.debug("Bed {} is {} (patient {})", bedId, stay.getStatus(), stay.getPatientID());
        return BedStatusResponse.builder()
                .bedId(bedId)
                .status(stay.getStatus().name())
                .patientId(stay.getPatientID())
                .build();
    }

    private PatientStay findById(Long stayId) {
        return patientStayRepository.findById(stayId)
                .orElseThrow(() -> {
                    log.warn("Patient stay not found with id {}", stayId);
                    return new ResourceNotFoundException("Patient stay not found with id: " + stayId);
                });
    }

    private PatientStayResponse toResponse(PatientStay stay) {
        return PatientStayResponse.builder()
                .stayId(stay.getStayID())
                .patientId(stay.getPatientID())
                .admissionDate(stay.getAdmissionDate())
                .currentUnitId(stay.getCurrentUnitID())
                .currentBedId(stay.getCurrentBedID())
                .attendingProviderId(stay.getAttendingProviderID())
                .status(stay.getStatus())
                .build();
    }
}
