package com.bedmaster.inventory.dto;

import com.bedmaster.inventory.enums.FacilityStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Facility status update request")
public class FacilityStatusUpdateDTO {

    /**
     * The new operational status to apply to the facility.
     * Allowed values: ACTIVE, INACTIVE.
     */
    @NotNull(message = "Status is required")
    @Schema(
            description = "New facility status",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE"}
    )
    private FacilityStatus status;
}