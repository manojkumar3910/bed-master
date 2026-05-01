package com.bedmaster.module.common.events;

import org.springframework.context.ApplicationEvent;

public class DischargeCompletedEvent extends ApplicationEvent {

    private final Long stayID;
    private final Long notifyUserId;

    public DischargeCompletedEvent(Object source, Long stayID, Long notifyUserId) {
        super(source);
        this.stayID = stayID;
        this.notifyUserId = notifyUserId;
    }

    public Long getStayID() { return stayID; }
    public Long getNotifyUserId() { return notifyUserId; }
}