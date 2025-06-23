package kr.ac.kumoh.ce.s20240058.cloudpetbackend.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CareLog;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.TodayDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.CareLogRepository;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.CarePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CareLogService {
    private final CareLogRepository careLogRepository;
    private final CarePlanRepository carePlanRepository;
    private final RepeatStrategyService repeatStrategyService;

    // update
    public TodayDto updateCareLogToggle(Long planId, LocalDate date) {
        CareLog careLog = careLogRepository
                .findByCarePlan_PlanIdAndRecordDate(planId, date);
        CarePlan carePlan = carePlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("CarePlan not found"));

        if (careLog == null) {
            careLog = CareLog.builder()
                    .recordDate(date)
                    .isDone(false)
                    .carePlan(carePlan)
                    .build();
        }

        careLog.setRecordDate(date);
        careLog.setIsDone(!careLog.getIsDone());
        CareLog saved = careLogRepository.save(careLog);

        return TodayDto.builder()
                .planId(planId)
                .planName(carePlan.getPlanName())
                .repeatStrategyDto(repeatStrategyService.getRepeatStrategy(carePlan.getRepeatStrategy()))
                .isDoneToday(saved.getIsDone())
                .build();
    }
}
