package com.bedmaster.inventory.repository;

import com.bedmaster.inventory.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    List<Unit> findByFacilityID(Integer facilityID);
}
