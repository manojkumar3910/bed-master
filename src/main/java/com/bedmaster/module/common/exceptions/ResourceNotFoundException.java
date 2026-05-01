package com.bedmaster.module.common.exceptions;

// Thrown when a requested resource (e.g. Notification) cannot be found in the database.
// Extends RuntimeException so Spring does not require callers to declare it with throws.
public class ResourceNotFoundException extends RuntimeException {

    // Use when you already have a full, descriptive message ready to pass directly.
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Convenience constructor: builds the message automatically from the resource name and its ID.
    // Example: new ResourceNotFoundException("Notification", 42L)
    //          produces → "Notification not found with id: 42"
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }
}
