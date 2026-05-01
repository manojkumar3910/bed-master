package com.bedmaster.module.module_7.repository;

import com.bedmaster.module.module_7.entity.DischargePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DischargePlanRepository extends JpaRepository<DischargePlan, Long> {
    // Corrected query method to support the Service logic
    Optional<DischargePlan> findByStayID(Long stayID);
}