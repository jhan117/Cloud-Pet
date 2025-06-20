package kr.ac.kumoh.ce.s20240058.cloudpetbackend.controller;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.TodayDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.service.CareLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://cloudpet-frontend.netlify.app/"})
@RequestMapping("/api/care-logs")
public class CareLogController {
    private final CareLogService careLogService;

    // 자주 업뎃 patch
    @PatchMapping("/{planId}")
    public TodayDto updateCareLogToggle(@PathVariable Long planId, @RequestBody LocalDate date) {
        return careLogService.updateCareLogToggle(planId, date);
    }
}
