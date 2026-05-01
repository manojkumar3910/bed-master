package com.bedmaster.module.module_9.repository;

import com.bedmaster.module.module_9.entity.Notification;
import com.bedmaster.module.module_9.enums.Category;
import com.bedmaster.module.module_9.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Data-access layer for the Notification entity.
// Spring Data JPA automatically implements all methods at runtime based on their names —
// no SQL or JPQL needs to be written manually.
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Retrieves every notification that belongs to the given user, regardless of status.
    // Used to populate the full notification inbox for a user.
    List<Notification> findByUserId(Long userId);

    // Retrieves only the notifications that match both the user and a specific status
    // (e.g. UNREAD). Used to show a filtered notification list or the unread badge.
    List<Notification> findByUserIdAndStatus(Long userId, Status status);

    // Retrieves all notifications that belong to a particular workflow category
    // (e.g. all CLEANING notifications across all users). Useful for admin reporting.
    List<Notification> findByCategory(Category category);

    // Returns the number of notifications for a user that match a given status.
    // Used to drive the unread count badge without loading all notification records.
    long countByUserIdAndStatus(Long userId, Status status);
}
