package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Room status update request")
public class RoomStatusUpdateDTO {

    @NotBlank(message = "Status is required")
    @Schema(description = "New room status", example = "OOS")
    private String status;
}
