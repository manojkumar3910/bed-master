package com.bedmaster.module.module_9.enums;

// Tracks whether the recipient has seen or acted on a notification.
// Stored as STRING in the database so column values remain human-readable.
public enum Status {
    UNREAD,    // Notification has not been viewed by the user yet
    READ,      // Notification has been viewed
    DISMISSED  // Notification has been explicitly dismissed and is no longer active
}
