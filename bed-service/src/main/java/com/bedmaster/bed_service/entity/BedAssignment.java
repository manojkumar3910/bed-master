package com.bedmaster.bed_service.entity;
import com.bedmaster.bed_service.enums.AssignmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "Bed_Assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    private Long requestId;
    private Long bedId;
    private String assignedBy;
    private LocalDateTime assignedDate;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;


}
