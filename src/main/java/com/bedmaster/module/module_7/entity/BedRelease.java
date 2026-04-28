package com.bedmaster.module.module_7.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long releaseID;

    private Long stayID;

    private Long bedID;

    private LocalDate releasedDate;

    private Boolean evsTriggered;

}
