package com.bedmaster.module.module_9.integration;

// -----------------------------------------------------------------------------
// Integration snippet — paste the relevant parts into your existing EvsService.
// Assumes CleanRequest has getBedId() and a Status enum with COMPLETED value.
// Assumes you can resolve the BedManager's userId from UserRepository or similar.
// -----------------------------------------------------------------------------

import com.bedmaster.module.module_9.enums.Category;
import com.bedmaster.module.module_9.service.NotificationService;

// ── Constructor injection (add to EvsService) ─────────────────────────────────
//
//   private final NotificationService notificationService;
//
//   public EvsService(CleanRequestRepository cleanRequestRepository,
//                     NotificationService notificationService) {
//       this.cleanRequestRepository = cleanRequestRepository;
//       this.notificationService = notificationService;
//   }

// ── Method where CleanRequest status is set to COMPLETED ─────────────────────
//
//   @Transactional
//   public CleanRequestDTO completeCleanRequest(Long requestId, Long bedManagerUserId) {
//       CleanRequest request = cleanRequestRepository.findById(requestId)
//               .orElseThrow(() -> new ResourceNotFoundException("CleanRequest", requestId));
//
//       request.setStatus(CleanRequest.Status.COMPLETED);
//       CleanRequest saved = cleanRequestRepository.save(request);
//
//       notificationService.createNotification(
//               bedManagerUserId,
//               "Cleaning completed for Bed #" + request.getBedId() + ". Bed is ready for assignment.",
//               Category.CLEANING
//       );
//
//       return cleanRequestMapper.toDTO(saved);
//   }

public class EvsServiceIntegration {
    // This file is a documentation-only integration guide.
    // No runnable code lives here — copy the snippets above into EvsService.java.
    private EvsServiceIntegration() {}
}
