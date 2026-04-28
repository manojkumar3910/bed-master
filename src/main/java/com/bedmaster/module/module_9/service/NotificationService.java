package com.bedmaster.notification.service;

import com.bedmaster.notification.dto.NotificationRequestDTO;
import com.bedmaster.notification.dto.NotificationResponseDTO;
import com.bedmaster.notification.entity.Notification;
import com.bedmaster.notification.enums.Category;
import com.bedmaster.notification.enums.Status;
import com.bedmaster.notification.exception.ResourceNotFoundException;
import com.bedmaster.notification.mapper.NotificationMapper;
import com.bedmaster.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Business logic layer for notifications.
// Accepts NotificationRequestDTO as input and returns NotificationResponseDTO as output —
// the Notification entity never escapes this class.
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    // Constructor injection ensures both dependencies are mandatory and makes the class easier to unit-test.
    public NotificationService(NotificationRepository notificationRepository,
                               NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    // Creates and persists a new notification for the given user.
    // Called by other services (EVS, BedAssignment, etc.) when a workflow event occurs.
    // status defaults to UNREAD and createdDate is stamped automatically by @PrePersist on the entity.
    @Transactional
    public NotificationResponseDTO createNotification(Long userId, String message, Category category) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setCategory(category);
        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponseDTO(saved);
    }

    // Accepts a NotificationRequestDTO from the controller and delegates to the core createNotification method.
    // Keeps the controller free from knowing how to unpack the request fields.
    @Transactional
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        return createNotification(requestDTO.getUserId(), requestDTO.getMessage(), requestDTO.getCategory());
    }

    // Returns every notification for the given user across all statuses (UNREAD, READ, DISMISSED).
    // Used to populate the full notification inbox view.
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Returns only UNREAD notifications for the given user.
    // Used to populate the notification dropdown/badge that shows pending items.
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatus(userId, Status.UNREAD)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Changes a notification's status from UNREAD to READ, indicating the user has seen it.
    // Throws ResourceNotFoundException (→ HTTP 404) if no notification exists with the given ID.
    @Transactional
    public NotificationResponseDTO markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        notification.setStatus(Status.READ);
        return notificationMapper.toResponseDTO(notificationRepository.save(notification));
    }

    // Changes a notification's status to DISMISSED, removing it from the active notification list.
    // Throws ResourceNotFoundException (→ HTTP 404) if no notification exists with the given ID.
    @Transactional
    public NotificationResponseDTO dismiss(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        notification.setStatus(Status.DISMISSED);
        return notificationMapper.toResponseDTO(notificationRepository.save(notification));
    }

    // Returns the total number of UNREAD notifications for the given user.
    // Used to drive the numeric badge on the notification bell icon in the UI.
    @Transactional(readOnly = true)
    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndStatus(userId, Status.UNREAD);
    }
}
