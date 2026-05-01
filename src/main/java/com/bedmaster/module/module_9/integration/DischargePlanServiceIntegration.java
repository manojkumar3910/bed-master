package com.bedmaster.module.module_9.integration;

// -----------------------------------------------------------------------------
// Integration snippet — paste the relevant parts into your existing DischargePlanService.
// Assumes DischargePlan has getStayId() and a Status enum with READY value.
// Assumes you can resolve the BedManager's userId responsible for the stay.
// -----------------------------------------------------------------------------

import com.bedmaster.module.module_9.enums.Category;
import com.bedmaster.module.module_9.service.NotificationService;

// ── Constructor injection (add to DischargePlanService) ──────────────────────
//
//   private final NotificationService notificationService;
//
//   public DischargePlanService(DischargePlanRepository dischargePlanRepository,
//                               NotificationService notificationService) {
//       this.dischargePlanRepository = dischargePlanRepository;
//       this.notificationService = notificationService;
//   }

// ── Method where DischargePlan status is set to READY ────────────────────────
//
//   @Transactional
//   public DischargePlanDTO markPlanAsReady(Long planId, Long bedManagerUserId) {
//       DischargePlan plan = dischargePlanRepository.findById(planId)
//               .orElseThrow(() -> new ResourceNotFoundException("DischargePlan", planId));
//
//       plan.setStatus(DischargePlan.Status.READY);
//       DischargePlan saved = dischargePlanRepository.save(plan);
//
//       notificationService.createNotification(
//               bedManagerUserId,
//               "Patient Stay #" + plan.getStayId() + " is ready for discharge. Initiate bed release.",
//               Category.DISCHARGE
//       );
//
//       return dischargePlanMapper.toDTO(saved);
//   }

public class DischargePlanServiceIntegration {
    private DischargePlanServiceIntegration() {}
}
