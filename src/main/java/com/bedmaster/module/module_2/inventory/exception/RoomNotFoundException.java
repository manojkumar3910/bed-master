package com.bedmaster.inventory.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Integer id) {
        super("Unit not found with id: " + id);
    }
}
