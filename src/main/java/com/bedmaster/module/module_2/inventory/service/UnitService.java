package com.bedmaster.inventory.service;


import com.bedmaster.inventory.entity.Facility;
import com.bedmaster.inventory.enums.FacilityStatus;
import com.bedmaster.inventory.dto.UnitRequestDTO;
import com.bedmaster.inventory.dto.UnitResponseDTO;
import com.bedmaster.inventory.entity.Unit;
import com.bedmaster.inventory.enums.UnitSpecialty;
import com.bedmaster.inventory.enums.UnitStatus;
import com.bedmaster.inventory.exception.FacilityNotFoundException;
import com.bedmaster.inventory.exception.InvalidStatusTransitionException;
import com.bedmaster.inventory.exception.UnitNotFoundException;
import com.bedmaster.inventory.repository.FacilityRepository;
import com.bedmaster.inventory.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final FacilityRepository facilityRepository;


    public UnitService(UnitRepository unitRepository,
                       FacilityRepository facilityRepository
                      ) {
        this.unitRepository = unitRepository;
        this.facilityRepository = facilityRepository;

    }

    public List<UnitResponseDTO> getUnitsByFacility(Integer facilityId) {
        return unitRepository.findByFacilityID(facilityId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UnitResponseDTO updateUnitStatus(Integer unitId, String status) {

        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new UnitNotFoundException(unitId));

        String oldStatus = unit.getStatus().name();

        try {
            unit.setStatus(
                    UnitStatus.valueOf(status.toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException(
                    "Invalid unit status: " + status
            );
        }

        Unit savedUnit = unitRepository.save(unit);


        return convertToDto(savedUnit);
    }

    public UnitResponseDTO createUnit(UnitRequestDTO dto) {

        Facility facility = facilityRepository.findById(dto.getFacilityID())
                .orElseThrow(() ->
                        new FacilityNotFoundException(dto.getFacilityID())
                );

        // ✅ CROSS‑LEVEL RULE
        if (facility.getStatus() == FacilityStatus.INACTIVE) {
            throw new InvalidStatusTransitionException(
                    "Cannot create unit under inactive facility"
            );
        }

        Unit unit = new Unit();
        unit.setFacilityID(dto.getFacilityID());
        unit.setName(dto.getName());
        unit.setCapacity(dto.getCapacity());

        unit.setSpecialty(
                UnitSpecialty.valueOf(dto.getSpecialty().toUpperCase())
        );

        unit.setStatus(
                UnitStatus.valueOf(dto.getStatus().toUpperCase())
        );

        return convertToDto(unitRepository.save(unit));
    }

    private UnitResponseDTO convertToDto(Unit unit) {
        UnitResponseDTO dto = new UnitResponseDTO();
        dto.setUnitID(unit.getUnitID());
        dto.setFacilityID(unit.getFacilityID());
        dto.setName(unit.getName());
        dto.setSpecialty(unit.getSpecialty().name());
        dto.setCapacity(unit.getCapacity());
        dto.setStatus(unit.getStatus().name());
        return dto;
    }
}