package com.bedmaster.module.module_7.service;

import com.bedmaster.module.common.events.BedReleasedEvent;
import com.bedmaster.module.common.exceptions.BusinessRuleViolationException;
import com.bedmaster.module.common.exceptions.ResourceNotFoundException;
import com.bedmaster.module.module_7.dto.BedReleaseRequestDTO;
import com.bedmaster.module.module_7.dto.BedReleaseResponseDTO;
import com.bedmaster.module.module_7.entity.BedRelease;
import com.bedmaster.module.module_7.entity.DischargePlan;
import com.bedmaster.module.module_7.enums.DischargeStatus;
import com.bedmaster.module.module_7.mapper.BedReleaseMapper;
import com.bedmaster.module.module_7.repository.BedReleaseRepository;
import com.bedmaster.module.module_7.repository.DischargePlanRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BedReleaseService {

    private final BedReleaseRepository bedReleaseRepository;
    private final DischargePlanRepository dischargePlanRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BedReleaseMapper mapper;

    public BedReleaseService(BedReleaseRepository bedReleaseRepository,
                             DischargePlanRepository dischargePlanRepository,
                             ApplicationEventPublisher eventPublisher,
                             BedReleaseMapper mapper) {
        this.bedReleaseRepository = bedReleaseRepository;
        this.dischargePlanRepository = dischargePlanRepository;
        this.eventPublisher = eventPublisher;
        this.mapper = mapper;
    }

    @Transactional
    public BedReleaseResponseDTO createRelease(BedReleaseRequestDTO dto) {
        DischargePlan plan = dischargePlanRepository.findByStayID(dto.getStayID())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "DischargePlan for Stay ID " + dto.getStayID(), dto.getStayID()));

        if (DischargeStatus.COMPLETED != plan.getStatus()) {
            throw new BusinessRuleViolationException(
                "Cannot release bed. Discharge for Stay ID " + dto.getStayID() + " is not completed yet.");
        }

        bedReleaseRepository.findByStayID(dto.getStayID()).ifPresent(existing -> {
            throw new BusinessRuleViolationException(
                "Bed has already been released for Stay ID: " + dto.getStayID());
        });

        BedRelease release = mapper.toEntity(dto);
        release.setReleasedDate(LocalDate.now());
        release.setEvsTriggered(true);

        BedRelease saved = bedReleaseRepository.save(release);

        // TODO: replace saved.getStayID() used as evsStaffId with the actual EVS staff ID
        // once a staff lookup is available for this unit.
        eventPublisher.publishEvent(
            new BedReleasedEvent(this, saved.getStayID(), saved.getBedID(), saved.getStayID())
        );

        return mapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<BedReleaseResponseDTO> getAll() {
        return bedReleaseRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BedReleaseResponseDTO getById(Long id) {
        BedRelease release = bedReleaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BedRelease", id));
        return mapper.toResponseDTO(release);
    }

    @Transactional
    public void delete(Long id) {
        if (!bedReleaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("BedRelease", id);
        }
        bedReleaseRepository.deleteById(id);
    }
}
