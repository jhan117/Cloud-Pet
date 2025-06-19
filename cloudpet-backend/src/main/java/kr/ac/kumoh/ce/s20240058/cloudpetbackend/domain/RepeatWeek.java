package kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RepeatWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repeatWeekId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "strategy_id", nullable = false)
    private RepeatStrategy strategy;

    @OneToMany(mappedBy = "repeatWeek", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepeatWeekDay> days;
}
