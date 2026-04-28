package com.bedmaster.inventory.repository;

import com.bedmaster.inventory.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {}