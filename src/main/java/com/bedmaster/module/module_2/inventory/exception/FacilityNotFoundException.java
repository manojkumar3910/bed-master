package com.bedmaster.inventory.exception;


    public class FacilityNotFoundException extends RuntimeException {
        public FacilityNotFoundException(Integer id) {
            super("Facility not found with id: " + id);
        }
    }
