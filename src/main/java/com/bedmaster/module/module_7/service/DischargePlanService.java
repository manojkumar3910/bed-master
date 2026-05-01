package com.bedmaster.module.module_7.service;

import com.bedmaster.module.common.events.DischargeCompletedEvent;
import com.bedmaster.module.common.exceptions.BusinessRuleViolationException;
import com.bedmaster.module.common.exceptions.ResourceNotFoundException;
import com.bedmaster.module.module_7.dto.DischargePlanRequestDTO;
import com.bedmaster.module.module_7.dto.DischargePlanResponseDTO;
import com.bedmaster.module.module_7.entity.DischargePlan;
import com.bedmaster.module.module_7.enums.DischargeStatus;
import com.bedmaster.module.module_7.mapper.DischargePlanMapper;
import com.bedmaster.module.module_7.repository.DischargePlanRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DischargePlanService {

    private final DischargePlanRepository repository;
    private final ApplicationEventPublisher eventPublisher;
    private final DischargePlanMapper mapper;

    public DischargePlanService(DischargePlanRepository repository,
                                ApplicationEventPublisher eventPublisher,
                                DischargePlanMapper mapper) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.mapper = mapper;
    }

    @Transactional
    public DischargePlanResponseDTO createPlan(DischargePlanRequestDTO dto) {
        repository.findByStayID(dto.getStayID()).ifPresent(existing -> {
            throw new BusinessRuleViolationException(
                "A discharge plan already exists for Stay ID: " + dto.getStayID());
        });
        DischargePlan saved = repository.save(mapper.toEntity(dto));
        return mapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<DischargePlanResponseDTO> getAllPlans() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DischargePlanResponseDTO getPlanById(Long id) {
        DischargePlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DischargePlan", id));
        return mapper.toResponseDTO(plan);
    }

    @Transactional
    public DischargePlanResponseDTO updatePlan(Long id, DischargePlanRequestDTO dto) {
        DischargePlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DischargePlan", id));

        // stayID is intentionally not updatable — a plan belongs to a stay permanently
        plan.setEdd(dto.getEdd());
        plan.setDisposition(dto.getDisposition());
        plan.setReadinessChecklistJSON(dto.getReadinessChecklistJSON());
        plan.setStatus(dto.getStatus());

        DischargePlan saved = repository.save(plan);

        // TODO: replace second saved.getStayID() with the actual responsible user ID
        // once a user/staff lookup is available (bed manager or charge nurse for this stay).
        if (DischargeStatus.COMPLETED == saved.getStatus()) {
            eventPublisher.publishEvent(
                new DischargeCompletedEvent(this, saved.getStayID(), saved.getStayID())
            );
        }

        return mapper.toResponseDTO(saved);
    }

    @Transactional
    public void deletePlan(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("DischargePlan", id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAllPlans() {
        repository.deleteAll();
    }
}
