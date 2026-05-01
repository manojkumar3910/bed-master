package com.bedmaster.module.module_7.dto;

import com.bedmaster.module.module_7.enums.DischargeStatus;

import java.time.LocalDate;
import java.util.Map;

public class DischargePlanResponseDTO {

    private Long dischargeID;
    private Long stayID;
    private LocalDate edd;
    private String disposition;
    private Map<String, Boolean> readinessChecklistJSON;
    private DischargeStatus status;

    public Long getDischargeID() { return dischargeID; }
    public void setDischargeID(Long dischargeID) { this.dischargeID = dischargeID; }

    public Long getStayID() { return stayID; }
    public void setStayID(Long stayID) { this.stayID = stayID; }

    public LocalDate getEdd() { return edd; }
    public void setEdd(LocalDate edd) { this.edd = edd; }

    public String getDisposition() { return disposition; }
    public void setDisposition(String disposition) { this.disposition = disposition; }

    public Map<String, Boolean> getReadinessChecklistJSON() { return readinessChecklistJSON; }
    public void setReadinessChecklistJSON(Map<String, Boolean> readinessChecklistJSON) { this.readinessChecklistJSON = readinessChecklistJSON; }

    public DischargeStatus getStatus() { return status; }
    public void setStatus(DischargeStatus status) { this.status = status; }
}
