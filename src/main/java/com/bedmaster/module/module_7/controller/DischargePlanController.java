package com.bedmaster.module.module_7.controller;

import com.bedmaster.module.module_7.entity.DischargePlan;
import com.bedmaster.module.module_7.service.DischargePlanService;

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
    public DischargePlan create(@RequestBody DischargePlan plan){

        return service.createPlan(plan);
    }

    @GetMapping
    public List<DischargePlan> getAll(){
        return service.getAllPlans();
    }

    @GetMapping("/{id}")
    public DischargePlan getById(@PathVariable Long id){
        return service.getPlanById(id);
    }

    @PutMapping("/{id}")
    public DischargePlan update(@PathVariable Long id,
                                @RequestBody DischargePlan plan){
        return service.updatePlan(id, plan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deletePlan(id);
    }

    @DeleteMapping("/all")
    public void deleteAll(){
        service.deleteAllPlans();
    }
}

