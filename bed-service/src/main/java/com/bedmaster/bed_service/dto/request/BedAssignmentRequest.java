package com.bedmaster.bed_service.dto.request;

import com.bedmaster.bed_service.enums.AssignmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body for assigning a bed to a patient")
public class BedAssignmentRequest {

    @NotNull(message = "Request ID is required")
    @Schema(description = "Patient/request identifier", example = "1001")
    private Long requestId;

    @NotNull(message = "Bed ID is required")
    @Schema(description = "Bed to assign", example = "201")
    private Long bedId;

    @NotBlank(message = "Assigned by is required")
    @Schema(description = "Name or ID of the staff assigning the bed", example = "Dr. Smith")
    private String assignedBy;

    @NotNull(message = "Status is required")
    @Schema(description = "Initial assignment status", example = "CONFIRMED")
    private AssignmentStatus status;
}
