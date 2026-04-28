package com.bedmaster.bed_service.service;

import com.bedmaster.bed_service.dto.request.TransferOrderRequest;
import com.bedmaster.bed_service.dto.response.TransferOrderResponse;
import com.bedmaster.bed_service.entity.TransferOrder;
import com.bedmaster.bed_service.enums.TransferReason;
import com.bedmaster.bed_service.enums.TransferStatus;
import com.bedmaster.bed_service.enums.TransferType;
import com.bedmaster.bed_service.exception.BadRequestException;
import com.bedmaster.bed_service.exception.ResourceNotFoundException;
import com.bedmaster.bed_service.repository.TransferOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferOrderServiceTest {

    @Mock
    private TransferOrderRepository transferOrderRepository;

    @InjectMocks
    private TransferOrderService transferOrderService;

    private TransferOrder requestedTransfer;
    private TransferOrder approvedTransfer;

    @BeforeEach
    void setUp() {
        requestedTransfer = TransferOrder.builder()
                .transferId(1L)
                .stayId(5001L)
                .fromUnitId(10L).fromBedId(101L)
                .toUnitId(20L).toBedId(202L)
                .transferType(TransferType.B2B)
                .reason(TransferReason.LEVEL_OF_CARE)
                .requestedBy("Nurse Johnson")
                .requestedDate(LocalDateTime.now())
                .status(TransferStatus.REQUESTED)
                .build();

        approvedTransfer = TransferOrder.builder()
                .transferId(2L)
                .stayId(5002L)
                .fromUnitId(10L).fromBedId(102L)
                .toUnitId(20L).toBedId(203L)
                .transferType(TransferType.U2U)
                .reason(TransferReason.ISOLATION)
                .requestedBy("Dr. Adams")
                .requestedDate(LocalDateTime.now())
                .status(TransferStatus.APPROVED)
                .build();
    }

    // ─── createTransferOrder ─────────────────────────────────────────────────

    @Test
    @DisplayName("createTransferOrder - should save with REQUESTED status and return response")
    void createTransferOrder_success() {
        TransferOrderRequest request = new TransferOrderRequest();
        request.setStayId(5001L);
        request.setFromUnitId(10L);
        request.setFromBedId(101L);
        request.setToUnitId(20L);
        request.setToBedId(202L);
        request.setTransferType(TransferType.B2B);
        request.setReason(TransferReason.LEVEL_OF_CARE);
        request.setRequestedBy("Nurse Johnson");

        when(transferOrderRepository.save(any(TransferOrder.class))).thenReturn(requestedTransfer);

        TransferOrderResponse response = transferOrderService.createTransferOrder(request);

        assertThat(response.getTransferId()).isEqualTo(1L);
        assertThat(response.getStatus()).isEqualTo(TransferStatus.REQUESTED);
        assertThat(response.getStayId()).isEqualTo(5001L);

        ArgumentCaptor<TransferOrder> captor = ArgumentCaptor.forClass(TransferOrder.class);
        verify(transferOrderRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(TransferStatus.REQUESTED);
        assertThat(captor.getValue().getRequestedDate()).isNotNull();
    }

    // ─── getAllTransfers ──────────────────────────────────────────────────────

    @Test
    @DisplayName("getAllTransfers - should return list of all transfer orders")
    void getAllTransfers_returnsList() {
        when(transferOrderRepository.findAll()).thenReturn(List.of(requestedTransfer, approvedTransfer));

        List<TransferOrderResponse> result = transferOrderService.getAllTransfers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTransferId()).isEqualTo(1L);
        assertThat(result.get(1).getTransferId()).isEqualTo(2L);
    }

    // ─── getTransferById ─────────────────────────────────────────────────────

    @Test
    @DisplayName("getTransferById - should return response when found")
    void getTransferById_found() {
        when(transferOrderRepository.findById(1L)).thenReturn(Optional.of(requestedTransfer));

        TransferOrderResponse response = transferOrderService.getTransferById(1L);

        assertThat(response.getTransferId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getTransferById - should throw ResourceNotFoundException when not found")
    void getTransferById_notFound() {
        when(transferOrderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transferOrderService.getTransferById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── approveTransfer ─────────────────────────────────────────────────────

    @Test
    @DisplayName("approveTransfer - should change status to APPROVED")
    void approveTransfer_success() {
        TransferOrder approved = TransferOrder.builder()
                .transferId(1L).stayId(5001L)
                .fromUnitId(10L).fromBedId(101L).toUnitId(20L).toBedId(202L)
                .transferType(TransferType.B2B).reason(TransferReason.LEVEL_OF_CARE)
                .requestedBy("Nurse Johnson").requestedDate(LocalDateTime.now())
                .status(TransferStatus.APPROVED).build();

        when(transferOrderRepository.findById(1L)).thenReturn(Optional.of(requestedTransfer));
        when(transferOrderRepository.save(requestedTransfer)).thenReturn(approved);

        TransferOrderResponse response = transferOrderService.approveTransfer(1L);

        assertThat(response.getStatus()).isEqualTo(TransferStatus.APPROVED);
        assertThat(requestedTransfer.getStatus()).isEqualTo(TransferStatus.APPROVED);
    }

    @Test
    @DisplayName("approveTransfer - should throw BadRequestException when status is not REQUESTED")
    void approveTransfer_wrongStatus_alreadyApproved() {
        when(transferOrderRepository.findById(2L)).thenReturn(Optional.of(approvedTransfer));

        assertThatThrownBy(() -> transferOrderService.approveTransfer(2L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("REQUESTED");

        verify(transferOrderRepository, never()).save(any());
    }

    @Test
    @DisplayName("approveTransfer - should throw ResourceNotFoundException when transfer not found")
    void approveTransfer_notFound() {
        when(transferOrderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transferOrderService.approveTransfer(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // ─── completeTransfer ────────────────────────────────────────────────────

    @Test
    @DisplayName("completeTransfer - should change status to COMPLETED and set executedDate")
    void completeTransfer_success() {
        TransferOrder completed = TransferOrder.builder()
                .transferId(2L).stayId(5002L)
                .fromUnitId(10L).fromBedId(102L).toUnitId(20L).toBedId(203L)
                .transferType(TransferType.U2U).reason(TransferReason.ISOLATION)
                .requestedBy("Dr. Adams").requestedDate(LocalDateTime.now())
                .executedDate(LocalDateTime.now())
                .status(TransferStatus.COMPLETED).build();

        when(transferOrderRepository.findById(2L)).thenReturn(Optional.of(approvedTransfer));
        when(transferOrderRepository.save(approvedTransfer)).thenReturn(completed);

        TransferOrderResponse response = transferOrderService.completeTransfer(2L);

        assertThat(response.getStatus()).isEqualTo(TransferStatus.COMPLETED);
        assertThat(approvedTransfer.getStatus()).isEqualTo(TransferStatus.COMPLETED);
        assertThat(approvedTransfer.getExecutedDate()).isNotNull();
    }

    @Test
    @DisplayName("completeTransfer - should throw BadRequestException when status is not APPROVED")
    void completeTransfer_wrongStatus_requested() {
        when(transferOrderRepository.findById(1L)).thenReturn(Optional.of(requestedTransfer));

        assertThatThrownBy(() -> transferOrderService.completeTransfer(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("APPROVED");

        verify(transferOrderRepository, never()).save(any());
    }

    // ─── cancelTransfer ───────────────────────────────────────────────────────

    @Test
    @DisplayName("cancelTransfer - should change status to CANCELLED when REQUESTED")
    void cancelTransfer_success_fromRequested() {
        TransferOrder cancelled = TransferOrder.builder()
                .transferId(1L).status(TransferStatus.CANCELLED).build();

        when(transferOrderRepository.findById(1L)).thenReturn(Optional.of(requestedTransfer));
        when(transferOrderRepository.save(requestedTransfer)).thenReturn(cancelled);

        TransferOrderResponse response = transferOrderService.cancelTransfer(1L);

        assertThat(response.getStatus()).isEqualTo(TransferStatus.CANCELLED);
        assertThat(requestedTransfer.getStatus()).isEqualTo(TransferStatus.CANCELLED);
    }

    @Test
    @DisplayName("cancelTransfer - should change status to CANCELLED when APPROVED")
    void cancelTransfer_success_fromApproved() {
        TransferOrder cancelled = TransferOrder.builder()
                .transferId(2L).status(TransferStatus.CANCELLED).build();

        when(transferOrderRepository.findById(2L)).thenReturn(Optional.of(approvedTransfer));
        when(transferOrderRepository.save(approvedTransfer)).thenReturn(cancelled);

        TransferOrderResponse response = transferOrderService.cancelTransfer(2L);

        assertThat(response.getStatus()).isEqualTo(TransferStatus.CANCELLED);
    }

    @Test
    @DisplayName("cancelTransfer - should throw BadRequestException when transfer is already COMPLETED")
    void cancelTransfer_alreadyCompleted() {
        TransferOrder completedTransfer = TransferOrder.builder()
                .transferId(3L).status(TransferStatus.COMPLETED).build();

        when(transferOrderRepository.findById(3L)).thenReturn(Optional.of(completedTransfer));

        assertThatThrownBy(() -> transferOrderService.cancelTransfer(3L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("completed");

        verify(transferOrderRepository, never()).save(any());
    }
}
