package kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.RepeatType;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RepeatStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strategyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepeatType type;

    @Column(nullable = false)
    private int intervalValue;

    @Column(nullable = false)
    private LocalDate startDate;
}
