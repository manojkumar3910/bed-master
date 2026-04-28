package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Room response")
public class RoomResponseDTO {

    @Schema(description = "Room ID", example = "200")
    private Integer roomID;

    @Schema(description = "Unit ID", example = "100")
    private Integer unitID;

    @Schema(description = "Room number", example = "R-101")
    private String roomNumber;

    @Schema(description = "Room gender allocation", example = "MALE")
    private String gender;

    @Schema(description = "Room status", example = "AVAILABLE")
    private String status;
}