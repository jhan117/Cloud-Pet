package kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.DayOfWeek;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RepeatWeekDayDto {
    private DayOfWeek dayOfWeek;
}
