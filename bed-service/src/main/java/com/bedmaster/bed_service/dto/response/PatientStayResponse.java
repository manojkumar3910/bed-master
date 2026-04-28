package com.bedmaster.bed_service.dto.response;

import com.bedmaster.bed_service.enums.StayStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Patient stay details")
public class PatientStayResponse {

    @Schema(description = "Unique stay identifier")
    private Long stayId;

    @Schema(description = "Patient identifier")
    private Long patientId;

    @Schema(description = "Date and time of admission")
    private LocalDateTime admissionDate;

    @Schema(description = "Current unit the patient is in")
    private Long currentUnitId;

    @Schema(description = "Current bed the patient is assigned to")
    private Long currentBedId;

    @Schema(description = "Attending provider identifier")
    private Long attendingProviderId;

    @Schema(description = "Current stay status", example = "ADMITTED")
    private StayStatus status;
}
