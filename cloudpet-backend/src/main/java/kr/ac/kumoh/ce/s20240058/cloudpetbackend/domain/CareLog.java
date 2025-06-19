package kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CareLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private Boolean isDone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id", nullable = false)
    private CarePlan carePlan;
}
