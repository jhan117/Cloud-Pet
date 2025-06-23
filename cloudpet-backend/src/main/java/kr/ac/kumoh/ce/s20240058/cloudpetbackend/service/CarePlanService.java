package kr.ac.kumoh.ce.s20240058.cloudpetbackend.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.RepeatStrategy;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.RepeatType;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.CarePlanDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.RepeatStrategyDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.RepeatWeekDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.TodayDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.CarePlanRepository;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.RepeatStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarePlanService {
    private final CarePlanRepository carePlanRepository;
    private final RepeatStrategyService repeatStrategyService;
    private final RepeatWeekService repeatWeekService;
    private final RepeatStrategyRepository repeatStrategyRepository;

    private CarePlanDto toDto(CarePlan carePlan) {
        return CarePlanDto.builder()
                .planId(carePlan.getPlanId())
                .planName(carePlan.getPlanName())
                .repeatStrategyDto(repeatStrategyService.getRepeatStrategy(carePlan.getRepeatStrategy()))
                .build();
    }

    // create
    public CarePlanDto createCarePlan(CarePlanDto dto) {
        RepeatStrategyDto repeatStrategyDto = repeatStrategyService.createRepeatStrategy(dto.getRepeatStrategyDto());
        RepeatStrategy repeatStrategy = repeatStrategyRepository.getReferenceById(repeatStrategyDto.getStrategyId());

        if (RepeatType.WEEK == dto.getRepeatStrategyDto().getType()) {
            repeatWeekService.createRepeatWeek(dto.getRepeatStrategyDto().getRepeatWeek(), repeatStrategy);
        }

        return toDto(carePlanRepository.save(CarePlan.builder()
                .planName(dto.getPlanName())
                .repeatStrategy(repeatStrategy)
                .build()));
    }

    // read
    public List<CarePlanDto> getAllCarePlans() {
        List<Object[]> rows = carePlanRepository.findAllCarePlansWithRepeatStrategy();
        List<CarePlanDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            Long planId = ((Number)row[0]).longValue();
            String planName = (String) row[1];
            Long strategyId = ((Number)row[2]).longValue();
            String type = (String) row[3];
            int intervalValue = ((Number)row[4]).intValue();
            LocalDate startDate = ((java.sql.Date)row[5]).toLocalDate();
            Long repeatWeekId = row[6] != null ? ((Number) row[6]).longValue() : null;

            RepeatWeekDto repeatWeekDto = null;
            if (repeatWeekId != null) {
                String daysConcat = (String) row[7];
                List<String> days = Arrays.asList(daysConcat.split(","));

                repeatWeekDto = RepeatWeekDto.builder()
                        .repeatWeekId(repeatWeekId)
                        .days(days)
                        .build();
            }

            RepeatStrategyDto repeatStrategyDto = RepeatStrategyDto.builder()
                    .strategyId(strategyId)
                    .type(RepeatType.valueOf(type))
                    .intervalValue(intervalValue)
                    .startDate(startDate)
                    .repeatWeek(repeatWeekDto)
                    .build();

            CarePlanDto carePlanDto = CarePlanDto.builder()
                    .planId(planId)
                    .planName(planName)
                    .repeatStrategyDto(repeatStrategyDto)
                    .build();

            result.add(carePlanDto);
        }
        return result;
    }

    // update
    public CarePlanDto updateCarePlan(Long planId, CarePlanDto dto) {
        if (planId == null) return null;
        CarePlan carePlan = carePlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("CarePlan not found"));

        Long strategyId = carePlan.getRepeatStrategy().getStrategyId();
        RepeatType beforeType = carePlan.getRepeatStrategy().getType();
        RepeatType afterType = dto.getRepeatStrategyDto().getType();

        repeatStrategyService.updateRepeatStrategy(strategyId, dto.getRepeatStrategyDto());
        RepeatStrategy repeatStrategy = repeatStrategyRepository.findById(strategyId)
                .orElseThrow(() -> new EntityNotFoundException("repeatStrategy not found"));

        if (beforeType == RepeatType.WEEK && afterType == RepeatType.WEEK) {
            repeatWeekService.updateRepeatWeek(strategyId, dto.getRepeatStrategyDto().getRepeatWeek());
        } else if (beforeType == RepeatType.WEEK) {
            repeatWeekService.deleteRepeatWeekByStrategyId(strategyId);
        } else if (afterType == RepeatType.WEEK) {
            repeatWeekService.createRepeatWeek(dto.getRepeatStrategyDto().getRepeatWeek(), repeatStrategy);
        }
        carePlan.setPlanName(dto.getPlanName());
        carePlan.setRepeatStrategy(repeatStrategy);
        return toDto(carePlanRepository.save(carePlan));
    }

    // delete
    public void deleteCarePlan(Long planId) {
        carePlanRepository.deleteById(planId);
    }

    // for Today Page
    public List<TodayDto> getAllTodayByDate(LocalDate date) {
        List<Object[]> rows = carePlanRepository.findAllTodayByDate(date);
        List<TodayDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            Long planId = ((Number) row[0]).longValue();
            String planName = (String) row[1];
            Long strategyId = ((Number) row[2]).longValue();
            String typeStr = (String) row[3];
            int intervalValue = ((Number) row[4]).intValue();
            LocalDate startDate = ((java.sql.Date) row[5]).toLocalDate();
            Long repeatWeekId = row[6] != null ? ((Number) row[6]).longValue() : null;
            Boolean isDone = (Boolean) row[8];

            RepeatWeekDto repeatWeekDto = null;
            if (repeatWeekId != null) {
                String daysConcat = (String) row[7];
                List<String> days = new ArrayList<>();
                if (daysConcat != null && !daysConcat.isEmpty()) {
                    days = Arrays.asList(daysConcat.split(","));
                }

                repeatWeekDto = RepeatWeekDto.builder()
                        .repeatWeekId(repeatWeekId)
                        .days(days)
                        .build();
            }

            RepeatStrategyDto repeatStrategyDto = RepeatStrategyDto.builder()
                    .strategyId(strategyId)
                    .type(RepeatType.valueOf(typeStr))
                    .intervalValue(intervalValue)
                    .startDate(startDate)
                    .repeatWeek(repeatWeekDto)
                    .build();

            TodayDto todayDto = TodayDto.builder()
                    .planId(planId)
                    .planName(planName)
                    .repeatStrategyDto(repeatStrategyDto)
                    .isDoneToday(isDone != null && isDone)
                    .build();

            result.add(todayDto);
        }
        return result;
    }
}
