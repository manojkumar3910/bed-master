package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Facility response")
public class FacilityResponseDTO {

    @Schema(
            description = "Facility ID",
            example = "10"
    )
    private Integer facilityID;

    @Schema(
            description = "Facility name",
            example = "City Hospital"
    )
    private String name;

    @Schema(
            description = "Campus or location",
            example = "Main Campus"
    )
    private String campus;

    @Schema(
            description = "Facility status",
            example = "ACTIVE"
    )
    private String status;

    public Integer getFacilityID() {
        return facilityID;
    }
    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCampus() {
        return campus;
    }
    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}