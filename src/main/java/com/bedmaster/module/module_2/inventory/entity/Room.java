package com.bedmaster.inventory.entity;

import com.bedmaster.inventory.enums.RoomGender;
import com.bedmaster.inventory.enums.RoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomID;

    @NotNull(message = "UnitID is required")
    private Integer unitID;

    @NotBlank(message = "Room number cannot be empty")
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is required")
    private RoomGender gender;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private RoomStatus status;

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomGender getGender() {
        return gender;
    }

    public void setGender(RoomGender gender) {
        this.gender = gender;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}