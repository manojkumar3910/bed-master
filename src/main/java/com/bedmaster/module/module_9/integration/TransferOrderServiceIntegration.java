package com.bedmaster.notification.integration;

// -----------------------------------------------------------------------------
// Integration snippet — paste the relevant parts into your existing TransferOrderService.
// Assumes TransferOrder has getStayId() and getToUnitChargeNurseUserId() (or equivalent).
// Assumes a Status enum with APPROVED value.
// -----------------------------------------------------------------------------

import com.bedmaster.notification.enums.Category;
import com.bedmaster.notification.service.NotificationService;

// ── Constructor injection (add to TransferOrderService) ──────────────────────
//
//   private final NotificationService notificationService;
//
//   public TransferOrderService(TransferOrderRepository transferOrderRepository,
//                               NotificationService notificationService) {
//       this.transferOrderRepository = transferOrderRepository;
//       this.notificationService = notificationService;
//   }

// ── Method where TransferOrder status is set to APPROVED ─────────────────────
//
//   @Transactional
//   public TransferOrderDTO approveTransferOrder(Long transferOrderId, Long toUnitChargeNurseUserId) {
//       TransferOrder order = transferOrderRepository.findById(transferOrderId)
//               .orElseThrow(() -> new ResourceNotFoundException("TransferOrder", transferOrderId));
//
//       order.setStatus(TransferOrder.Status.APPROVED);
//       TransferOrder saved = transferOrderRepository.save(order);
//
//       notificationService.createNotification(
//               toUnitChargeNurseUserId,
//               "Transfer approved for Stay #" + order.getStayId() + ". Prepare to receive patient.",
//               Category.TRANSFER
//       );
//
//       return transferOrderMapper.toDTO(saved);
//   }

public class TransferOrderServiceIntegration {
    private TransferOrderServiceIntegration() {}
}
