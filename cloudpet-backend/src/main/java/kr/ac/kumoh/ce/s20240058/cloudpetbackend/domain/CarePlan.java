package kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CarePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(nullable = false)
    private String planName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "strategy_id", nullable = false)
    private RepeatStrategy repeatStrategy;
}
