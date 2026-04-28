package com.bedmaster.bed_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Bed status information")
public class BedStatusResponse {

    @Schema(description = "Bed identifier")
    private Long bedId;

    @Schema(description = "Current bed status: AVAILABLE, ADMITTED, TRANSFERRED, or DISCHARGED", example = "AVAILABLE")
    private String status;

    @Schema(description = "Patient ID currently assigned to the bed (null if available)")
    private Long patientId;
}
