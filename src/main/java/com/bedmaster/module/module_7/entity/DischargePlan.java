package com.bedmaster.module.module_7.entity;

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

    @Column(unique = true, nullable = false) // Ensures one plan per patient stay
    private Long stayID;

    private LocalDate edd; // Estimated Discharge Date [cite: 176]
    private String disposition; // Home/Rehab/SNF [cite: 177]

    @ElementCollection
    @CollectionTable(name = "readiness_checklist", joinColumns = @JoinColumn(name = "discharge_id"))
    @MapKeyColumn(name = "check_item")
    @Column(name = "is_completed")
    private Map<String, Boolean> readinessChecklistJSON; // [cite: 178]

    private String status;
}

