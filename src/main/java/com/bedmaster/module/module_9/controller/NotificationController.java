package com.bedmaster.notification.controller;

import com.bedmaster.notification.dto.NotificationRequestDTO;
import com.bedmaster.notification.dto.NotificationResponseDTO;
import com.bedmaster.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller that exposes notification endpoints under /api/v1/notifications.
// Contains no business logic — every method delegates directly to NotificationService.
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // Constructor injection — makes the dependency explicit and testable without a Spring context.
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /api/v1/notifications/user/{userId}
    // Returns all notifications for the specified user regardless of status.
    // HTTP 200 OK with the full notification list in the response body.
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getAllForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    // GET /api/v1/notifications/user/{userId}/unread
    // Returns only UNREAD notifications for the specified user.
    // HTTP 200 OK; an empty list is returned if the user has no unread notifications.
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnreadForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    // GET /api/v1/notifications/user/{userId}/count
    // Returns the plain count of UNREAD notifications for the specified user as a long.
    // HTTP 200 OK; used by the frontend to render the notification badge number.
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.countUnread(userId));
    }

    // PATCH /api/v1/notifications/{id}/read
    // Marks the specified notification as READ and returns the updated notification.
    // HTTP 200 OK on success; HTTP 404 if the notification does not exist.
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    // PATCH /api/v1/notifications/{id}/dismiss
    // Marks the specified notification as DISMISSED and returns the updated notification.
    // HTTP 200 OK on success; HTTP 404 if the notification does not exist.
    @PatchMapping("/{id}/dismiss")
    public ResponseEntity<NotificationResponseDTO> dismiss(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.dismiss(id));
    }

    // POST /api/v1/notifications
    // Creates a new notification from the JSON body supplied by the caller.
    // Accepts NotificationRequestDTO — notificationId, status, and createdDate must NOT be sent;
    // they are generated/set automatically by the database and JPA.
    // HTTP 201 Created with the full NotificationResponseDTO (including generated fields) in the body.
    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestBody NotificationRequestDTO requestDTO) {
        NotificationResponseDTO response = notificationService.createNotification(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
