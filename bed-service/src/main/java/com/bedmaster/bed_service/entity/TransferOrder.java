package com.bedmaster.bed_service.entity;

import com.bedmaster.bed_service.enums.TransferReason;
import com.bedmaster.bed_service.enums.TransferStatus;
import com.bedmaster.bed_service.enums.TransferType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transfer_Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransferOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long transferId;

        private Long stayId;

        private Long fromUnitId;

        private Long fromBedId;

        private Long toUnitId;

        private Long toBedId;

        @Enumerated(EnumType.STRING)
        private TransferType transferType;

        @Enumerated(EnumType.STRING)
        private TransferStatus status;

        @Enumerated(EnumType.STRING)
        private TransferReason reason;

        private String requestedBy;

        private LocalDateTime requestedDate;

        private LocalDateTime executedDate;

    // --- Enums for restricted values ---

}
