package com.bedmaster.module.module_9.listener;

import com.bedmaster.module.common.events.BedReleasedEvent;
import com.bedmaster.module.module_9.enums.Category;
import com.bedmaster.module.module_9.service.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BedReleaseNotificationListener {

    private final NotificationService notificationService;

    public BedReleaseNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onBedReleased(BedReleasedEvent event) {
        notificationService.createNotification(
            event.getEvsStaffId(),
            "Bed #" + event.getBedId() + " is vacant (Stay #" + event.getStayID() + "). Cleaning required before next assignment.",
            Category.CLEANING
        );
    }
}
