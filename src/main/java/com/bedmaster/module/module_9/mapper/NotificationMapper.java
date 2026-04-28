package com.bedmaster.notification.mapper;

import com.bedmaster.notification.dto.NotificationRequestDTO;
import com.bedmaster.notification.dto.NotificationResponseDTO;
import com.bedmaster.notification.entity.Notification;
import org.springframework.stereotype.Component;

// Responsible for converting between the Notification JPA entity and the request/response DTOs.
// Keeps mapping logic in one place so neither the service nor the controller
// needs to know the internal structure of any type.
@Component
public class NotificationMapper {

    // Converts a Notification entity (from the database) into a NotificationResponseDTO safe for API responses.
    // Includes all fields — notificationId, status, and createdDate are populated by the DB/JPA lifecycle.
    // Returns null if the input is null, preventing NullPointerExceptions in the service layer.
    public NotificationResponseDTO toResponseDTO(Notification notification) {
        if (notification == null) {
            return null;
        }
        return new NotificationResponseDTO(
                notification.getNotificationId(),
                notification.getUserId(),
                notification.getMessage(),
                notification.getCategory(),
                notification.getStatus(),
                notification.getCreatedDate()
        );
    }

    // Converts an incoming NotificationRequestDTO (from an HTTP request body) into a Notification entity
    // ready to be persisted. notificationId, status, and createdDate are intentionally omitted —
    // the DB generates notificationId, @PrePersist sets createdDate, and status defaults to UNREAD.
    // Returns null if the input is null.
    public Notification toEntity(NotificationRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setMessage(dto.getMessage());
        notification.setCategory(dto.getCategory());
        return notification;
    }
}
