package com.bedmaster.inventory.entity;

import com.bedmaster.inventory.enums.UnitSpecialty;
import com.bedmaster.inventory.enums.UnitStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unitID;

    @NotNull(message = "FacilityID is required")
    private Integer facilityID;

    @NotBlank(message = "Unit name cannot be empty")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Specialty is required")
    private UnitSpecialty specialty;

    @PositiveOrZero(message = "Capacity cannot be negative")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private UnitStatus status;

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

    public UnitSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(UnitSpecialty specialty) {
        this.specialty = specialty;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }
}