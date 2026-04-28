package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bed readiness response")
public class BedReadinessResponseDTO {

    @Schema(
            description = "Indicates whether bed is ready for assignment",
            example = "true"
    )
    private boolean ready;

    @Schema(
            description = "Reason if bed is not ready",
            example = "ROOM_OOS"
    )
    private String reason;

    // ✅ No‑args constructor (required by Jackson)
    public BedReadinessResponseDTO() {
    }

    // ✅ Constructor used by BedService
    public BedReadinessResponseDTO(boolean ready, String reason) {
        this.ready = ready;
        this.reason = reason;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
