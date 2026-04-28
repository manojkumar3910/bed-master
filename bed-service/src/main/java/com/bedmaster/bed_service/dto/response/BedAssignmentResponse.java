package com.bedmaster.bed_service.dto.response;

import com.bedmaster.bed_service.enums.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedAssignmentResponse {
    private Long assignmentId;
    private Long requestId;
    private Long bedId;
    private String assignedBy;
    private LocalDateTime assignedDate;
    private AssignmentStatus status;
}
