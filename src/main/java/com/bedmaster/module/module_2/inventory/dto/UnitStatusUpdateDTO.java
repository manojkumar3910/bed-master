package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Unit status update request")
public class UnitStatusUpdateDTO {

    @NotBlank(message = "Status is required")
    @Schema(description = "New unit status", example = "INACTIVE")
    private String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}