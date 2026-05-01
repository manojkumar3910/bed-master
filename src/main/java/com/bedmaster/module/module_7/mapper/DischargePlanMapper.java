package com.bedmaster.module.module_7.mapper;

import com.bedmaster.module.module_7.dto.DischargePlanRequestDTO;
import com.bedmaster.module.module_7.dto.DischargePlanResponseDTO;
import com.bedmaster.module.module_7.entity.DischargePlan;
import org.springframework.stereotype.Component;

@Component
public class DischargePlanMapper {

    public DischargePlan toEntity(DischargePlanRequestDTO dto) {
        if (dto == null) return null;
        DischargePlan entity = new DischargePlan();
        entity.setStayID(dto.getStayID());
        entity.setEdd(dto.getEdd());
        entity.setDisposition(dto.getDisposition());
        entity.setReadinessChecklistJSON(dto.getReadinessChecklistJSON());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public DischargePlanResponseDTO toResponseDTO(DischargePlan entity) {
        if (entity == null) return null;
        DischargePlanResponseDTO dto = new DischargePlanResponseDTO();
        dto.setDischargeID(entity.getDischargeID());
        dto.setStayID(entity.getStayID());
        dto.setEdd(entity.getEdd());
        dto.setDisposition(entity.getDisposition());
        dto.setReadinessChecklistJSON(entity.getReadinessChecklistJSON());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
