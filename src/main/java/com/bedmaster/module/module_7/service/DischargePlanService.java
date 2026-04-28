package com.bedmaster.module.module_7.service;

import com.bedmaster.module.module_7.entity.DischargePlan;
import com.bedmaster.module.module_7.repository.DischargePlanRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class DischargePlanService {

    private final DischargePlanRepository repository;

    public DischargePlanService(DischargePlanRepository repository) {

        this.repository = repository;
    }

    public DischargePlan createPlan(DischargePlan plan) {

        repository.findByStayID(plan.getStayID()).ifPresent(existing -> {
            throw new RuntimeException("A discharge plan already exists for Stay ID: " + plan.getStayID());
        });
        return repository.save(plan);
    }

    public List<DischargePlan> getAllPlans() {
        return repository.findAll();
    }

    public DischargePlan getPlanById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public DischargePlan updatePlan(Long id, DischargePlan updatedPlan) {

        DischargePlan plan = repository.findById(id).orElseThrow();

        plan.setStayID(updatedPlan.getStayID());
        plan.setEdd(updatedPlan.getEdd());
        plan.setDisposition(updatedPlan.getDisposition());
        plan.setReadinessChecklistJSON(updatedPlan.getReadinessChecklistJSON());
        plan.setStatus(updatedPlan.getStatus());

        return repository.save(plan);
    }

    public void deletePlan(Long id) {

        // 1. Check if the plan exists using the ID
        if (!repository.existsById(id)) {
            // 2. If not found, throw a clear exception for the GlobalExceptionHandler
            throw new RuntimeException("Discharge Plan with ID " + id + " does not exist. Cannot delete.");
        }
        repository.deleteById(id);
    }

    public void deleteAllPlans() {
        repository.deleteAll();
    }
}
