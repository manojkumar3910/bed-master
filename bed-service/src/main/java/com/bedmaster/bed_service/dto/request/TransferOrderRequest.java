package com.bedmaster.bed_service.dto.request;

import com.bedmaster.bed_service.enums.TransferReason;
import com.bedmaster.bed_service.enums.TransferType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body for creating a patient transfer order")
public class TransferOrderRequest {

    @NotNull(message = "Stay ID is required")
    @Schema(description = "Patient stay identifier", example = "5001")
    private Long stayId;

    @NotNull(message = "From unit ID is required")
    @Schema(description = "Unit the patient is transferring from", example = "10")
    private Long fromUnitId;

    @NotNull(message = "From bed ID is required")
    @Schema(description = "Bed the patient is transferring from", example = "101")
    private Long fromBedId;

    @NotNull(message = "To unit ID is required")
    @Schema(description = "Unit the patient is transferring to", example = "20")
    private Long toUnitId;

    @NotNull(message = "To bed ID is required")
    @Schema(description = "Bed the patient is transferring to", example = "202")
    private Long toBedId;

    @NotNull(message = "Transfer type is required")
    @Schema(description = "Type of transfer: A2B (Admit-to-Bed), B2B (Bed-to-Bed), U2U (Unit-to-Unit)", example = "B2B")
    private TransferType transferType;

    @NotNull(message = "Transfer reason is required")
    @Schema(description = "Reason for transfer", example = "LEVEL_OF_CARE")
    private TransferReason reason;

    @NotBlank(message = "Requested by is required")
    @Schema(description = "Name or ID of the staff requesting the transfer", example = "Nurse Johnson")
    private String requestedBy;
}
