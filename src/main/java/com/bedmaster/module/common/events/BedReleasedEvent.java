package com.bedmaster.module.common.events;

import org.springframework.context.ApplicationEvent;

public class BedReleasedEvent extends ApplicationEvent {

    private final Long stayID;
    private final Long bedId;
    private final Long evsStaffId;

    public BedReleasedEvent(Object source, Long stayID, Long bedId, Long evsStaffId) {
        super(source);
        this.stayID = stayID;
        this.bedId = bedId;
        this.evsStaffId = evsStaffId;
    }

    public Long getStayID() { return stayID; }
    public Long getBedId() { return bedId; }
    public Long getEvsStaffId() { return evsStaffId; }
}
