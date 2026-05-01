package com.bedmaster.module.module_7.controller;

import com.bedmaster.module.module_7.dto.BedReleaseRequestDTO;
import com.bedmaster.module.module_7.dto.BedReleaseResponseDTO;
import com.bedmaster.module.module_7.service.BedReleaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bedrelease")
public class BedReleaseController {

    private final BedReleaseService service;

    public BedReleaseController(BedReleaseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BedReleaseResponseDTO> create(@RequestBody BedReleaseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRelease(dto));
    }

    @GetMapping
    public ResponseEntity<List<BedReleaseResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedReleaseResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
