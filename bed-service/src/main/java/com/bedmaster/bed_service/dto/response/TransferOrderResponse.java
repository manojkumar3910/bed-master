package com.bedmaster.bed_service.dto.response;

import com.bedmaster.bed_service.enums.TransferReason;
import com.bedmaster.bed_service.enums.TransferStatus;
import com.bedmaster.bed_service.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferOrderResponse {
    private Long transferId;
    private Long stayId;
    private Long fromUnitId;
    private Long fromBedId;
    private Long toUnitId;
    private Long toBedId;
    private TransferType transferType;
    private TransferStatus status;
    private TransferReason reason;
    private String requestedBy;
    private LocalDateTime requestedDate;
    private LocalDateTime executedDate;
}
