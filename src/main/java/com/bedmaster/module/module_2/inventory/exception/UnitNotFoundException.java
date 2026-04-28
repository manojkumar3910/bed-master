package com.bedmaster.inventory.exception;


public class UnitNotFoundException extends RuntimeException {
    public UnitNotFoundException(Integer id) {
        super("Unit not found with id: " + id);
    }
}

