package com.bedmaster.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Unit creation request")
public class UnitRequestDTO {

    @NotNull(message = "Facility ID is required")
    @Schema(description = "Facility ID", example = "10")
    private Integer facilityID;

    @NotBlank(message = "Unit name is required")
    @Schema(description = "Unit name", example = "ICU Unit A")
    private String name;

    @NotBlank(message = "Specialty is required")
    @Schema(description = "Unit specialty", example = "ICU")
    private String specialty;

    @NotNull(message = "Capacity is required")
    @Schema(description = "Unit capacity", example = "20")
    private Integer capacity;

    @NotBlank(message = "Status is required")
    @Schema(description = "Unit status", example = "ACTIVE")
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
