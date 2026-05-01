package com.bedmaster.module.module_7.repository;

import com.bedmaster.module.module_7.entity.BedRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BedReleaseRepository extends JpaRepository<BedRelease, Long> {
    Optional<BedRelease> findByStayID(Long stayID);
}