package com.bedmaster.bed_service.entity;
import com.bedmaster.bed_service.enums.StayStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Patient_Stay")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientStay {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StayID;

    private Long PatientID;
    private LocalDateTime AdmissionDate;
    private Long CurrentUnitID;
    private Long CurrentBedID;
    private Long AttendingProviderID;

    @Enumerated(EnumType.STRING)
    private StayStatus status;

}
