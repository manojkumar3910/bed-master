package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unit response")
public class UnitResponseDTO {

    @Schema(description = "Unit ID", example = "100")
    private Integer unitID;

    @Schema(description = "Facility ID", example = "10")
    private Integer facilityID;

    @Schema(description = "Unit name", example = "ICU Unit A")
    private String name;

    @Schema(description = "Unit specialty", example = "ICU")
    private String specialty;

    @Schema(description = "Unit capacity", example = "20")
    private Integer capacity;

    @Schema(description = "Unit status", example = "ACTIVE")
    private String status;

    public Integer getUnitID() {
        return unitID;
    }
    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }

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

    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}