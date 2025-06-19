package kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RepeatWeekDto {
    private Long repeatWeekId; // back -> front 전용
    private List<String> days;
}
