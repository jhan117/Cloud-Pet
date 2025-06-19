package kr.ac.kumoh.ce.s20240058.cloudpetbackend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CareLogDto {
    private Long logId; // back -> front 전용
    private LocalDate recordDate;
    private Boolean isDone;
}
