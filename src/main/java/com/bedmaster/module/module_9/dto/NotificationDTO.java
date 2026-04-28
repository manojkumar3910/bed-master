package com.bedmaster.notification.dto;

import com.bedmaster.notification.enums.Category;
import com.bedmaster.notification.enums.Status;

import java.time.LocalDateTime;

// Data Transfer Object for Notification — the only representation of a notification
// that crosses the service boundary into the controller or is accepted from an HTTP request body.
// Keeps the JPA entity out of the API layer entirely.
public class NotificationDTO {

    private Long notificationId; // Null on incoming create requests; populated on all responses
    private Long userId;         // ID of the user who receives this notification
    private String message;      // Notification text shown in the UI
    private Category category;   // Workflow category (ASSIGNMENT, CLEANING, etc.)
    private Status status;       // Current read state (UNREAD, READ, DISMISSED)
    private LocalDateTime createdDate; // Set by the entity's @PrePersist; included in responses

    // No-arg constructor required by Jackson to deserialize incoming JSON request bodies.
    public NotificationDTO() {}

    // All-args constructor used by NotificationMapper when converting an entity to a DTO for responses.
    public NotificationDTO(Long notificationId, Long userId, String message,
                           Category category, Status status, LocalDateTime createdDate) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.category = category;
        this.status = status;
        this.createdDate = createdDate;
    }

    // Getters and setters — Jackson uses these to serialize responses and deserialize request bodies.
    public Long getNotificationId() { return notificationId; }
    public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
