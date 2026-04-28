package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Bed response")
public class BedResponseDTO {

    @Schema(description = "Bed ID", example = "300")
    private Integer bedId; // ✅ camelCase

    @Schema(description = "Bed number", example = "B-01")
    private String bedNumber;

    @Schema(description = "Bed type", example = "STANDARD")
    private String bedType;

    @Schema(description = "Bed status", example = "AVAILABLE")
    private String status;

    @Schema(
            description = "Enabled bed attributes only",
            example = "[\"TelemetryBed\"]"
    )
    private List<String> attributes;

    // ===== Getters & Setters =====

    public Integer getBedId() {
        return bedId;
    }

    public void setBedId(Integer bedId) {
        this.bedId = bedId;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
}