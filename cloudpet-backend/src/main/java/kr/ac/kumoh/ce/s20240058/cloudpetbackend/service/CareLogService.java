package kr.ac.kumoh.ce.s20240058.cloudpetbackend.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CareLog;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.CareLogDto;
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

    // update
    public CareLog updateCareLogToggle(Long planId, LocalDate date) {
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
        return careLogRepository.save(careLog);
    }
}
