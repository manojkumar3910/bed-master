package com.bedmaster.notification.dto;

import com.bedmaster.notification.enums.Category;
import com.bedmaster.notification.enums.Status;

import java.time.LocalDateTime;

// Represents the JSON body the server returns for any notification-related response.
// Includes all fields — the DB-generated notificationId, the auto-set createdDate,
// and the current status — so the client has a complete picture of the record.
public class NotificationResponseDTO {

    private Long notificationId;       // Auto-generated primary key assigned by the database
    private Long userId;               // ID of the user who received this notification
    private String message;            // Notification text displayed in the UI
    private Category category;         // Workflow category of the notification
    private Status status;             // Current read state: UNREAD, READ, or DISMISSED
    private LocalDateTime createdDate; // Timestamp set automatically at insert time

    // No-arg constructor required by Jackson to serialize the response body.
    public NotificationResponseDTO() {}

    // All-args constructor used by NotificationMapper when building a response from a saved entity.
    public NotificationResponseDTO(Long notificationId, Long userId, String message,
                                   Category category, Status status, LocalDateTime createdDate) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.category = category;
        this.status = status;
        this.createdDate = createdDate;
    }

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
