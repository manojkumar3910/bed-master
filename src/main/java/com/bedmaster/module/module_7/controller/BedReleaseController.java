package com.bedmaster.module.module_7.controller;

import com.bedmaster.module.module_7.entity.BedRelease;
import com.bedmaster.module.module_7.service.BedReleaseService;
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
    public BedRelease create(@RequestBody BedRelease release){
        return service.createRelease(release);
    }

    @GetMapping
    public List<BedRelease> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BedRelease getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        service.delete(id);

    }
}