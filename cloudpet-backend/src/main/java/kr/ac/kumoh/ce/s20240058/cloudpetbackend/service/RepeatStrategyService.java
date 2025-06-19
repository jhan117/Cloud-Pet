package kr.ac.kumoh.ce.s20240058.cloudpetbackend.service;

import jakarta.persistence.EntityNotFoundException;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.*;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.RepeatType;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.RepeatStrategyDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.RepeatWeekDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.RepeatStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class RepeatStrategyService {
    private final RepeatStrategyRepository repeatStrategyRepository;
    private final RepeatWeekService repeatWeekService;

    public RepeatStrategy createRepeatStrategy(RepeatStrategyDto dto) {
        return repeatStrategyRepository.save(RepeatStrategy.builder()
                .type(dto.getType())
                .intervalValue(dto.getIntervalValue())
                .startDate(LocalDate.now())
                .build());
    }

    public RepeatStrategyDto getRepeatStrategy(RepeatStrategy entity) {
        RepeatWeekDto repeatWeekDto = null;
        if (entity.getType() == RepeatType.WEEK)
        {
            repeatWeekDto = repeatWeekService.getRepeatWeek(entity.getStrategyId());
        }

        return RepeatStrategyDto.builder()
                .strategyId(entity.getStrategyId())
                .type(entity.getType())
                .intervalValue(entity.getIntervalValue())
                .startDate(entity.getStartDate())
                .repeatWeek(repeatWeekDto)
                .build();
    }

    public RepeatStrategy updateRepeatStrategy(Long strategyId, RepeatStrategyDto dto) {
        RepeatStrategy strategy = repeatStrategyRepository.findById(strategyId)
                .orElseThrow(() -> new EntityNotFoundException("RepeatStrategy not found"));

        strategy.setType(dto.getType());
        strategy.setIntervalValue(dto.getIntervalValue());

        return repeatStrategyRepository.save(strategy);
    }
}
