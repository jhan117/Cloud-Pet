package kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.RepeatType;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RepeatStrategyDto {
    private Long strategyId; // back -> front 전용
    private RepeatType type;
    private int intervalValue;
    private LocalDate startDate;
    private RepeatWeekDto repeatWeek;
}
