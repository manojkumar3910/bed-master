package com.bedmaster.bed_service.controller;

import com.bedmaster.bed_service.dto.request.TransferOrderRequest;
import com.bedmaster.bed_service.dto.response.TransferOrderResponse;
import com.bedmaster.bed_service.service.TransferOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfer Orders", description = "APIs for managing patient transfer orders between beds and units")
public class TransferOrderController {

    private final TransferOrderService transferOrderService;

    @PostMapping
    @Operation(
            summary = "Create a transfer order",
            description = "Initiates a patient transfer request. Status starts as REQUESTED."
    )
    public ResponseEntity<TransferOrderResponse> createTransferOrder(@Valid @RequestBody TransferOrderRequest request) {
        log.info("POST /api/transfers - stayId={}, from bed {} to bed {}", request.getStayId(), request.getFromBedId(), request.getToBedId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transferOrderService.createTransferOrder(request));
    }

    @GetMapping
    @Operation(summary = "Get all transfer orders")
    public ResponseEntity<List<TransferOrderResponse>> getAllTransfers() {
        return ResponseEntity.ok(transferOrderService.getAllTransfers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transfer order by ID")
    public ResponseEntity<TransferOrderResponse> getTransferById(
            @Parameter(description = "Transfer order ID") @PathVariable Long id) {
        return ResponseEntity.ok(transferOrderService.getTransferById(id));
    }

    @PutMapping("/{id}/approve")
    @Operation(
            summary = "Approve a transfer order",
            description = "Approves a transfer order. Must be in REQUESTED status."
    )
    public ResponseEntity<TransferOrderResponse> approveTransfer(
            @Parameter(description = "Transfer order ID") @PathVariable Long id) {
        return ResponseEntity.ok(transferOrderService.approveTransfer(id));
    }

    @PutMapping("/{id}/complete")
    @Operation(
            summary = "Complete a transfer order",
            description = "Marks a transfer as completed and records the execution time. Must be in APPROVED status."
    )
    public ResponseEntity<TransferOrderResponse> completeTransfer(
            @Parameter(description = "Transfer order ID") @PathVariable Long id) {
        return ResponseEntity.ok(transferOrderService.completeTransfer(id));
    }

    @PutMapping("/{id}/cancel")
    @Operation(
            summary = "Cancel a transfer order",
            description = "Cancels a transfer order. Cannot cancel a COMPLETED transfer."
    )
    public ResponseEntity<TransferOrderResponse> cancelTransfer(
            @Parameter(description = "Transfer order ID") @PathVariable Long id) {
        return ResponseEntity.ok(transferOrderService.cancelTransfer(id));
    }
}
