package com.bedmaster.module.module_9.listener;

import com.bedmaster.module.common.events.DischargeCompletedEvent;
import com.bedmaster.module.module_9.enums.Category;
import com.bedmaster.module.module_9.service.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DischargeNotificationListener {

    private final NotificationService notificationService;

    public DischargeNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Fires AFTER the discharge transaction commits successfully.
    // REQUIRES_NEW opens a fresh transaction for the notification save —
    // without it, Spring sees the committed transaction synchronization still active
    // and the save silently does nothing.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onDischargeCompleted(DischargeCompletedEvent event) {
        notificationService.createNotification(
            event.getNotifyUserId(),
                "Patient Stay #" + event.getStayID() + " discharge completed",
            Category.DISCHARGE
        );
    }
}
