package com.bedmaster.bed_service.service;

import com.bedmaster.bed_service.dto.request.TransferOrderRequest;
import com.bedmaster.bed_service.dto.response.TransferOrderResponse;
import com.bedmaster.bed_service.entity.TransferOrder;
import com.bedmaster.bed_service.enums.TransferStatus;
import com.bedmaster.bed_service.exception.BadRequestException;
import com.bedmaster.bed_service.exception.ResourceNotFoundException;
import com.bedmaster.bed_service.repository.TransferOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferOrderService {

    private final TransferOrderRepository transferOrderRepository;

    @Transactional
    public TransferOrderResponse createTransferOrder(TransferOrderRequest request) {
        log.info("Creating transfer order for stay {} from bed {} to bed {}",
                request.getStayId(), request.getFromBedId(), request.getToBedId());

        TransferOrder transfer = TransferOrder.builder()
                .stayId(request.getStayId())
                .fromUnitId(request.getFromUnitId())
                .fromBedId(request.getFromBedId())
                .toUnitId(request.getToUnitId())
                .toBedId(request.getToBedId())
                .transferType(request.getTransferType())
                .reason(request.getReason())
                .requestedBy(request.getRequestedBy())
                .requestedDate(LocalDateTime.now())
                .status(TransferStatus.REQUESTED)
                .build();

        TransferOrderResponse response = toResponse(transferOrderRepository.save(transfer));
        log.debug("Transfer order created with id {}", response.getTransferId());
        return response;
    }

    public List<TransferOrderResponse> getAllTransfers() {
        log.debug("Fetching all transfer orders");
        return transferOrderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TransferOrderResponse getTransferById(Long id) {
        log.debug("Fetching transfer order with id {}", id);
        return toResponse(findById(id));
    }

    @Transactional
    public TransferOrderResponse approveTransfer(Long id) {
        log.info("Approving transfer order {}", id);
        TransferOrder transfer = findById(id);
        if (transfer.getStatus() != TransferStatus.REQUESTED) {
            log.warn("Cannot approve transfer {}: current status is {}", id, transfer.getStatus());
            throw new BadRequestException(
                    "Transfer can only be approved when in REQUESTED status. Current status: " + transfer.getStatus());
        }
        transfer.setStatus(TransferStatus.APPROVED);
        log.info("Transfer order {} approved", id);
        return toResponse(transferOrderRepository.save(transfer));
    }

    @Transactional
    public TransferOrderResponse completeTransfer(Long id) {
        log.info("Completing transfer order {}", id);
        TransferOrder transfer = findById(id);
        if (transfer.getStatus() != TransferStatus.APPROVED) {
            log.warn("Cannot complete transfer {}: current status is {}", id, transfer.getStatus());
            throw new BadRequestException(
                    "Transfer can only be completed when in APPROVED status. Current status: " + transfer.getStatus());
        }
        transfer.setStatus(TransferStatus.COMPLETED);
        transfer.setExecutedDate(LocalDateTime.now());
        log.info("Transfer order {} completed", id);
        return toResponse(transferOrderRepository.save(transfer));
    }

    @Transactional
    public TransferOrderResponse cancelTransfer(Long id) {
        log.info("Cancelling transfer order {}", id);
        TransferOrder transfer = findById(id);
        if (transfer.getStatus() == TransferStatus.COMPLETED) {
            log.warn("Cannot cancel transfer {}: already completed", id);
            throw new BadRequestException("Cannot cancel a transfer that has already been completed");
        }
        transfer.setStatus(TransferStatus.CANCELLED);
        log.info("Transfer order {} cancelled", id);
        return toResponse(transferOrderRepository.save(transfer));
    }

    private TransferOrder findById(Long id) {
        return transferOrderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Transfer order not found with id {}", id);
                    return new ResourceNotFoundException("Transfer order not found with id: " + id);
                });
    }

    private TransferOrderResponse toResponse(TransferOrder t) {
        return TransferOrderResponse.builder()
                .transferId(t.getTransferId())
                .stayId(t.getStayId())
                .fromUnitId(t.getFromUnitId())
                .fromBedId(t.getFromBedId())
                .toUnitId(t.getToUnitId())
                .toBedId(t.getToBedId())
                .transferType(t.getTransferType())
                .status(t.getStatus())
                .reason(t.getReason())
                .requestedBy(t.getRequestedBy())
                .requestedDate(t.getRequestedDate())
                .executedDate(t.getExecutedDate())
                .build();
    }
}
