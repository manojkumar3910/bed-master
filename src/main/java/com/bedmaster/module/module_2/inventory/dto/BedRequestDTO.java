package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@Schema(description = "Bed creation request")
public class BedRequestDTO {

    @NotNull(message = "Room ID is required")
    @Schema(description = "Room ID", example = "200")
    private Integer roomID;

    @NotBlank(message = "Bed number is required")
    @Schema(description = "Bed number", example = "B-01")
    private String bedNumber;

    @NotBlank(message = "Bed type is required")
    @Schema(description = "Bed type", example = "STANDARD")
    private String bedType;

    @NotBlank(message = "Status is required")
    @Schema(description = "Bed status", example = "AVAILABLE")
    private String status;

    // ✅ NEW: Attributes / Telemetry JSON
    @Schema(
            description = "Additional bed attributes (telemetry, capabilities)",
            example = "{\"telemetry\": true, \"bariatric\": false}"
    )
    private Map<String, Object> attributes;

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}