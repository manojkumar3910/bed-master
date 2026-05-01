package com.bedmaster.module.module_7.dto;

import java.time.LocalDate;

public class BedReleaseResponseDTO {

    private Long releaseID;
    private Long stayID;
    private Long bedID;
    private LocalDate releasedDate;
    private Boolean evsTriggered;

    public Long getReleaseID() { return releaseID; }
    public void setReleaseID(Long releaseID) { this.releaseID = releaseID; }

    public Long getStayID() { return stayID; }
    public void setStayID(Long stayID) { this.stayID = stayID; }

    public Long getBedID() { return bedID; }
    public void setBedID(Long bedID) { this.bedID = bedID; }

    public LocalDate getReleasedDate() { return releasedDate; }
    public void setReleasedDate(LocalDate releasedDate) { this.releasedDate = releasedDate; }

    public Boolean getEvsTriggered() { return evsTriggered; }
    public void setEvsTriggered(Boolean evsTriggered) { this.evsTriggered = evsTriggered; }
}
