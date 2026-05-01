package com.bedmaster.module.module_7.entity;

import com.bedmaster.module.module_7.enums.DischargeStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "discharge_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DischargePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dischargeID;

    @Column(unique = true, nullable = false)
    private Long stayID;

    private LocalDate edd;
    private String disposition;

    @ElementCollection
    @CollectionTable(name = "readiness_checklist", joinColumns = @JoinColumn(name = "discharge_id"))
    @MapKeyColumn(name = "check_item")
    @Column(name = "is_completed")
    private Map<String, Boolean> readinessChecklistJSON;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DischargeStatus status;
}
