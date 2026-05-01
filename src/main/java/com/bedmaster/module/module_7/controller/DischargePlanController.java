package com.bedmaster.module.module_7.controller;

import com.bedmaster.module.module_7.dto.DischargePlanRequestDTO;
import com.bedmaster.module.module_7.dto.DischargePlanResponseDTO;
import com.bedmaster.module.module_7.service.DischargePlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discharge")
public class DischargePlanController {

    private final DischargePlanService service;

    public DischargePlanController(DischargePlanService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DischargePlanResponseDTO> create(@RequestBody DischargePlanRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPlan(dto));
    }

    @GetMapping
    public ResponseEntity<List<DischargePlanResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DischargePlanResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DischargePlanResponseDTO> update(@PathVariable Long id,
                                                           @RequestBody DischargePlanRequestDTO dto) {
        return ResponseEntity.ok(service.updatePlan(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAllPlans();
        return ResponseEntity.noContent().build();
    }
}
