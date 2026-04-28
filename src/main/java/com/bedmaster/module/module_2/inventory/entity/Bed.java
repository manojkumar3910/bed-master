package com.bedmaster.inventory.entity;

import com.bedmaster.inventory.enums.BedStatus;
import com.bedmaster.inventory.enums.BedType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bed") // ✅ use lowercase to match DB table
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bedID;

    @NotNull(message = "RoomID is required")
    private Integer roomID;

    @NotBlank(message = "Bed number cannot be empty")
    private String bedNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Bed type is required")
    private BedType bedType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private BedStatus status;

    // ✅ FIXED: proper Java naming + DB column mapping
    @Column(name = "attributes_json", columnDefinition = "JSON")
    private String attributesJson;

    // ===== Getters & Setters =====

    public Integer getBedID() {
        return bedID;
    }

    public void setBedID(Integer bedID) {
        this.bedID = bedID;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public BedStatus getStatus() {
        return status;
    }

    public void setStatus(BedStatus status) {
        this.status = status;
    }

    // ✅ These methods are REQUIRED by BedService
    public String getAttributesJson() {
        return attributesJson;
    }

    public void setAttributesJson(String attributesJson) {
        this.attributesJson = attributesJson;
    }
}