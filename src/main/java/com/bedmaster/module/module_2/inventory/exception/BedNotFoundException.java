package com.bedmaster.inventory.exception;

public class BedNotFoundException extends RuntimeException {
    public BedNotFoundException(Integer id) {
        super("Bed not found with id: " + id);
    }
}
