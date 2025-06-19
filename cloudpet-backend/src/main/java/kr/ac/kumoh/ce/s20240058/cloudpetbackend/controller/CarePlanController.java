package kr.ac.kumoh.ce.s20240058.cloudpetbackend.controller;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.CarePlanDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.TodayDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.service.CarePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/care-plans")
public class CarePlanController {
    private final CarePlanService carePlanService;

    // care plan 기본 CRUD
    @GetMapping
    public List<CarePlanDto> getAllCarePlans() {
        return carePlanService.getAllCarePlans();
    }
    @PostMapping
    public CarePlan createCarePlan(@RequestBody CarePlanDto request) {
        return carePlanService.createCarePlan(request);
    }
    @PutMapping("/{planId}")
    public CarePlan updateCarePlan(@PathVariable Long planId, @RequestBody CarePlanDto request) {
        return carePlanService.updateCarePlan(planId, request);
    }
    @DeleteMapping("/{planId}")
    public void deleteCarePlan(@PathVariable Long planId) {
        carePlanService.deleteCarePlan(planId);
    }

    // for Today Page
    @GetMapping("/filter")
    public List<TodayDto> getAllCarePlansByDate(@RequestParam("date")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return carePlanService.getAllTodayByDate(date);
    }
}
