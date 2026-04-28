package com.bedmaster.notification.integration;

// -----------------------------------------------------------------------------
// Integration snippet — paste the relevant parts into your existing BedAssignmentService.
// Assumes BedAssignment has getBedId() and a Status enum with CONFIRMED value.
// Assumes you can resolve the ChargeNurse's userId for the relevant unit.
// -----------------------------------------------------------------------------

import com.bedmaster.notification.enums.Category;
import com.bedmaster.notification.service.NotificationService;

// ── Constructor injection (add to BedAssignmentService) ──────────────────────
//
//   private final NotificationService notificationService;
//
//   public BedAssignmentService(BedAssignmentRepository bedAssignmentRepository,
//                               NotificationService notificationService) {
//       this.bedAssignmentRepository = bedAssignmentRepository;
//       this.notificationService = notificationService;
//   }

// ── Method where BedAssignment status is set to CONFIRMED ────────────────────
//
//   @Transactional
//   public BedAssignmentDTO confirmAssignment(Long assignmentId, Long chargeNurseUserId) {
//       BedAssignment assignment = bedAssignmentRepository.findById(assignmentId)
//               .orElseThrow(() -> new ResourceNotFoundException("BedAssignment", assignmentId));
//
//       assignment.setStatus(BedAssignment.Status.CONFIRMED);
//       BedAssignment saved = bedAssignmentRepository.save(assignment);
//
//       notificationService.createNotification(
//               chargeNurseUserId,
//               "Bed #" + assignment.getBedId() + " has been assigned. Patient incoming to your unit.",
//               Category.ASSIGNMENT
//       );
//
//       return bedAssignmentMapper.toDTO(saved);
//   }

public class BedAssignmentServiceIntegration {
    private BedAssignmentServiceIntegration() {}
}
