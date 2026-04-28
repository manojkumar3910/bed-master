package com.bedmaster.inventory.service;

import com.bedmaster.inventory.dto.FacilityRequestDTO;
import com.bedmaster.inventory.dto.FacilityResponseDTO;
import com.bedmaster.inventory.entity.Facility;
import com.bedmaster.inventory.enums.FacilityStatus;
import com.bedmaster.inventory.exception.FacilityNotFoundException;
import com.bedmaster.inventory.exception.InvalidStatusTransitionException;
import com.bedmaster.inventory.repository.FacilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public List<FacilityResponseDTO> getAllFacilities() {
        return facilityRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FacilityResponseDTO saveFacility(FacilityRequestDTO dto) {

        Facility facility = new Facility();
        facility.setName(dto.getName());
        facility.setCampus(dto.getCampus());

        try {
            facility.setStatus(
                    FacilityStatus.valueOf(dto.getStatus().toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException(
                    "Invalid facility status: " + dto.getStatus()
            );
        }

        return convertToDto(facilityRepository.save(facility));
    }

    private FacilityResponseDTO convertToDto(Facility facility) {
        FacilityResponseDTO dto = new FacilityResponseDTO();
        dto.setFacilityID(facility.getFacilityID());
        dto.setName(facility.getName());
        dto.setCampus(facility.getCampus());
        dto.setStatus(facility.getStatus().name());
        return dto;
    }
}