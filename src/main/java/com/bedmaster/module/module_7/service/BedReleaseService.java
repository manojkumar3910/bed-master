package com.bedmaster.module.module_7.service;


import com.bedmaster.module.module_7.entity.BedRelease;
import com.bedmaster.module.module_7.entity.DischargePlan;

import com.bedmaster.module.module_7.repository.BedReleaseRepository;
import com.bedmaster.module.module_7.repository.DischargePlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BedReleaseService {
    private final BedReleaseRepository bedReleaseRepository;
    private final DischargePlanRepository dischargePlanRepository;

    public BedReleaseService(BedReleaseRepository bedReleaseRepository,
                             DischargePlanRepository dischargePlanRepository) {
        this.bedReleaseRepository = bedReleaseRepository;
        this.dischargePlanRepository = dischargePlanRepository;
    }

    @Transactional
    public BedRelease createRelease(BedRelease release) {
        // 1. Find the plan (using the stayID you send from Postman)
        DischargePlan plan = dischargePlanRepository.findByStayID(release.getStayID())
                .orElseThrow(() -> new RuntimeException("No Discharge Plan found for Stay ID: " + release.getStayID()));

        // 2. Update status to 'Completed' - This clears the patient from the Flow Board
        if(!"COMPLETED".equalsIgnoreCase(plan.getStatus())){
            throw new RuntimeException("Cannot release bed.Discharge not completed yet.");
        }

        release.setReleasedDate(LocalDate.now());
        release.setEvsTriggered(true);

        return bedReleaseRepository.save(release);
    }

    public List<BedRelease> getAll() {
        return bedReleaseRepository.findAll();
    }

    public BedRelease getById(Long id) {
        return bedReleaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed Release record " + id + " not found."));
    }

    public void delete(Long id) {

        if (!bedReleaseRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Bed Release record with ID " + id + " not found.");
        }
            bedReleaseRepository.deleteById(id);
    }
}