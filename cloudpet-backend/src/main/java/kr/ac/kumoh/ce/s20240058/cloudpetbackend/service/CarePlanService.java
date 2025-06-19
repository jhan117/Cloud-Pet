package kr.ac.kumoh.ce.s20240058.cloudpetbackend.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CareLog;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.RepeatStrategy;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.RepeatType;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.CarePlanDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.TodayDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.CareLogRepository;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.CarePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarePlanService {
    private final CarePlanRepository carePlanRepository;
    private final RepeatStrategyService repeatStrategyService;
    private final RepeatWeekService repeatWeekService;
    private final CareLogRepository careLogRepository;

    // create
    public CarePlan createCarePlan(CarePlanDto dto) {
        RepeatStrategy repeatStrategy = repeatStrategyService.createRepeatStrategy(dto.getRepeatStrategyDto());

        if (RepeatType.WEEK == dto.getRepeatStrategyDto().getType()) {
            repeatWeekService.createRepeatWeek(dto.getRepeatStrategyDto().getRepeatWeek(), repeatStrategy);
        }

        CarePlan carePlan = CarePlan.builder()
                .planName(dto.getPlanName())
                .repeatStrategy(repeatStrategy)
                .build();

        return carePlanRepository.save(carePlan);
    }

    // read
    public List<CarePlanDto> getAllCarePlans() {
        return carePlanRepository.findAll().stream()
                .map(carePlan -> CarePlanDto.builder()
                        .planId(carePlan.getPlanId())
                        .planName(carePlan.getPlanName())
                        .repeatStrategyDto(repeatStrategyService.getRepeatStrategy(carePlan.getRepeatStrategy()))
                        .build())
                .toList();
    }

    // update
    public CarePlan updateCarePlan(Long planId, CarePlanDto dto) {
        if (planId == null) return null;

        CarePlan carePlan = carePlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("CarePlan not found"));

        Long strategyId = carePlan.getRepeatStrategy().getStrategyId();
        RepeatType beforeType = carePlan.getRepeatStrategy().getType();
        RepeatType afterType = dto.getRepeatStrategyDto().getType();

        RepeatStrategy repeatStrategy = repeatStrategyService.updateRepeatStrategy(strategyId, dto.getRepeatStrategyDto());

        if (beforeType == RepeatType.WEEK && afterType == RepeatType.WEEK) {
            repeatWeekService.updateRepeatWeek(strategyId, dto.getRepeatStrategyDto().getRepeatWeek());
        } else if (beforeType == RepeatType.WEEK) {
            repeatWeekService.deleteRepeatWeekByStrategyId(strategyId);
        } else if (afterType == RepeatType.WEEK) {
            repeatWeekService.createRepeatWeek(dto.getRepeatStrategyDto().getRepeatWeek(), repeatStrategy);
        }

        carePlan.setPlanName(dto.getPlanName());
        carePlan.setRepeatStrategy(repeatStrategy);
        return carePlanRepository.save(carePlan);
    }

    // delete
    public void deleteCarePlan(Long planId) {
        carePlanRepository.deleteById(planId);
    }

    // for Today Page
    public List<TodayDto> getAllTodayByDate(LocalDate date) {
        return getAllCarePlans().stream().filter(carePlan -> {
            LocalDate startDate = carePlan.getRepeatStrategyDto().getStartDate();
            int interval = carePlan.getRepeatStrategyDto().getIntervalValue();

            return switch (carePlan.getRepeatStrategyDto().getType()) {
                case DAY -> ChronoUnit.DAYS.between(startDate, date) % interval == 0;
                case WEEK -> (ChronoUnit.WEEKS.between(startDate, date) % interval == 0)
                        && carePlan.getRepeatStrategyDto().getRepeatWeek().getDays()
                        .contains(date.getDayOfWeek().name().substring(0, 3));
                case MONTH -> (ChronoUnit.MONTHS.between(startDate, date) % interval == 0)
                        && startDate.getDayOfMonth() == date.getDayOfMonth();
                case YEAR -> (ChronoUnit.YEARS.between(startDate, date) % interval == 0)
                        && startDate.getDayOfMonth() == date.getDayOfMonth()
                        && startDate.getMonth() == date.getMonth();
            };
        })
        .map(carePlan -> {
            CareLog log = careLogRepository
                    .findByCarePlan_PlanIdAndRecordDate(carePlan.getPlanId(), date);

            boolean isDone = log != null && log.getIsDone();
            return TodayDto.builder()
                    .planId(carePlan.getPlanId())
                    .planName(carePlan.getPlanName())
                    .repeatStrategyDto(carePlan.getRepeatStrategyDto())
                    .isDoneToday(isDone)
                    .build();
        })
        .toList();
    }
}
