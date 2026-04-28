package com.bedmaster.notification.entity;

import com.bedmaster.notification.enums.Category;
import com.bedmaster.notification.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;

// JPA entity mapped to the "notification" table.
// Represents a single in-app notification sent to a BedMaster user.
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    // Foreign key to the User table — identifies who should receive this notification.
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Human-readable notification text shown in the UI (max 500 characters).
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // Set automatically by @PrePersist — never supplied by the caller and never updated after insert.
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // Called by JPA automatically just before a new row is inserted.
    // Stamps the creation timestamp and defaults status to UNREAD if the caller did not set it.
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        if (this.status == null) {
            this.status = Status.UNREAD;
        }
    }

    // No-arg constructor required by JPA to instantiate entities when loading from the database.
    public Notification() {}

    // Getters and setters — standard accessors used by the service layer and the mapper.
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
