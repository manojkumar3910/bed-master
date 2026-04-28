package com.bedmaster.notification.enums;

// Classifies which workflow event produced a notification.
// Stored as STRING in the database so column values remain human-readable.
public enum Category {
    ASSIGNMENT,  // A bed has been assigned to a patient
    CLEANING,    // A cleaning request has been completed
    ISOLATION,   // An isolation-related event has occurred
    TRANSFER,    // A transfer order has been approved
    DISCHARGE    // A discharge plan is ready for action
}
