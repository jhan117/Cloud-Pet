package kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TodayDto {
    private Long planId; // back -> front 전용
    private String planName;
    private RepeatStrategyDto repeatStrategyDto;
    private Boolean isDoneToday;
}
