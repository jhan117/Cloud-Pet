package kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.RepeatWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatWeekRepository extends JpaRepository<RepeatWeek,Long> {
    RepeatWeek findByStrategy_StrategyId(Long strategyId);
}
