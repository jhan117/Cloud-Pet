package kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.enums.DayOfWeek;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RepeatWeekDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repeatWeekDayId;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "repeat_week_id", nullable = false)
    private RepeatWeek repeatWeek;
}
