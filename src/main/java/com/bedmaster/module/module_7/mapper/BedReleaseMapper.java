package com.bedmaster.module.module_7.mapper;

import com.bedmaster.module.module_7.dto.BedReleaseRequestDTO;
import com.bedmaster.module.module_7.dto.BedReleaseResponseDTO;
import com.bedmaster.module.module_7.entity.BedRelease;
import org.springframework.stereotype.Component;

@Component
public class BedReleaseMapper {

    public BedRelease toEntity(BedReleaseRequestDTO dto) {
        if (dto == null) return null;
        BedRelease entity = new BedRelease();
        entity.setStayID(dto.getStayID());
        entity.setBedID(dto.getBedID());
        return entity;
    }

    public BedReleaseResponseDTO toResponseDTO(BedRelease entity) {
        if (entity == null) return null;
        BedReleaseResponseDTO dto = new BedReleaseResponseDTO();
        dto.setReleaseID(entity.getReleaseID());
        dto.setStayID(entity.getStayID());
        dto.setBedID(entity.getBedID());
        dto.setReleasedDate(entity.getReleasedDate());
        dto.setEvsTriggered(entity.getEvsTriggered());
        return dto;
    }
}
