package com.bedmaster.inventory.entity;

import com.bedmaster.inventory.enums.FacilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Facility")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facilityID;

    @NotBlank(message = "Facility name cannot be empty")
    private String name;

    @NotBlank(message = "Campus cannot be empty")
    private String campus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FacilityStatus status;

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

    // ✅ FIXED GETTER
    public FacilityStatus getStatus() {
        return status;
    }

    // ✅ FIXED SETTER
    public void setStatus(FacilityStatus status) {
        this.status = status;
    }
}