package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Bed status update request")
public class BedStatusUpdateDTO {

    @NotBlank(message = "Status is required")
    @Schema(description = "New bed status", example = "OCCUPIED")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}