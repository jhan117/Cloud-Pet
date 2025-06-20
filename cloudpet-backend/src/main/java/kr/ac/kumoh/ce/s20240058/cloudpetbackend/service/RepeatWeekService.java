package kr.ac.kumoh.ce.s20240058.cloudpetbackend.service;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.RepeatStrategy;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.RepeatWeek;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.RepeatWeekDay;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.DayOfWeek;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto.RepeatWeekDto;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository.RepeatWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RepeatWeekService {
    private final RepeatWeekRepository repeatWeekRepository;

    public void createRepeatWeek(RepeatWeekDto dto, RepeatStrategy repeatStrategy) {
        RepeatWeek repeatWeek = RepeatWeek.builder()
                .strategy(repeatStrategy)
                .build();

        List<RepeatWeekDay> repeatWeekDays = dto
                .getDays()
                .stream()
                .map(dayStr -> RepeatWeekDay.builder()
                        .dayOfWeek(DayOfWeek.valueOf(dayStr))
                        .repeatWeek(repeatWeek)
                        .build())
                .collect(Collectors.toList());

        repeatWeek.setDays(repeatWeekDays);
        repeatWeekRepository.save(repeatWeek);
    }

    public RepeatWeekDto getRepeatWeek(Long strategyId) {
        RepeatWeek repeatWeek = repeatWeekRepository.findByStrategy_StrategyId(strategyId);

        if (repeatWeek == null) return null;

        return RepeatWeekDto.builder()
                .repeatWeekId(strategyId)
                .days(repeatWeek.getDays().stream()
                        .map(day -> day.getDayOfWeek().name())
                        .toList())
                .build();
    }

    public void updateRepeatWeek(Long strategyId, RepeatWeekDto dto) {
        RepeatWeek repeatWeek = repeatWeekRepository.findByStrategy_StrategyId(strategyId);

        repeatWeek.getDays().clear();

        List<RepeatWeekDay> newDays = dto.getDays().stream()
                .map(dayStr -> RepeatWeekDay.builder()
                        .dayOfWeek(DayOfWeek.valueOf(dayStr))
                        .repeatWeek(repeatWeek)
                        .build())
                .toList();

        repeatWeek.getDays().addAll(newDays);
        repeatWeekRepository.save(repeatWeek);
    }

    public void deleteRepeatWeekByStrategyId(Long strategyId) {
        RepeatWeek repeatWeek = repeatWeekRepository.findByStrategy_StrategyId(strategyId);
        repeatWeekRepository.delete(repeatWeek);
        repeatWeekRepository.flush();
    }
}
