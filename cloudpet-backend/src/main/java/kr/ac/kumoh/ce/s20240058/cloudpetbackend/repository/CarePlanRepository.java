package kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarePlanRepository extends JpaRepository<CarePlan,Long> {

    @Query(value = """
        SELECT
           cp.plan_id, cp.plan_name,
           rs.strategy_id, rs.type, rs.interval_value, rs.start_date,
           rw.repeat_week_id,
           GROUP_CONCAT(rwd.day_of_week ORDER BY rwd.day_of_week SEPARATOR ',') days
       FROM
           care_plan cp
           JOIN repeat_strategy rs ON cp.strategy_id = rs.strategy_id
           LEFT JOIN repeat_week rw ON rw.strategy_id = rs.strategy_id
           LEFT JOIN repeat_week_day rwd ON rwd.repeat_week_id = rw.repeat_week_id
       GROUP BY
           cp.plan_id, cp.plan_name,
           rs.strategy_id, rs.type, rs.interval_value, rs.start_date,
           rw.repeat_week_id
       ORDER BY
           cp.plan_id;               
        """, nativeQuery = true)
    List<Object[]> findAllCarePlansWithRepeatStrategy();

    @Query(value = """
        SELECT
           cp.plan_id, cp.plan_name,
           rs.strategy_id, rs.type, rs.interval_value, rs.start_date,
           rw.repeat_week_id,
           GROUP_CONCAT(rwd.day_of_week ORDER BY rwd.day_of_week SEPARATOR ',') days,
           cl.is_done
       FROM
           care_plan cp
           JOIN repeat_strategy rs ON cp.strategy_id = rs.strategy_id
           LEFT JOIN repeat_week rw ON rw.strategy_id = rs.strategy_id
           LEFT JOIN repeat_week_day rwd ON rwd.repeat_week_id = rw.repeat_week_id
           LEFT JOIN care_log cl ON cl.plan_id = cp.plan_id AND cl.record_date = :recordDate
       WHERE
           (rs.type = 'DAY' AND MOD(DATEDIFF(:recordDate, rs.start_date), rs.interval_value) = 0)
           OR
           (
               rs.type = 'WEEK'
               AND MOD(TIMESTAMPDIFF(WEEK, rs.start_date, :recordDate), rs.interval_value) = 0
               AND EXISTS (
                   SELECT 1
                   FROM repeat_week rw2
                   JOIN repeat_week_day rwd2 ON rw2.repeat_week_id = rwd2.repeat_week_id
                   WHERE
                       rw2.strategy_id = rs.strategy_id
                       AND rwd2.day_of_week = UPPER(LEFT(DAYNAME(:recordDate), 3))
               )
           )
           OR
           (
               rs.type = 'MONTH'
               AND MOD(TIMESTAMPDIFF(MONTH, rs.start_date, :recordDate), rs.interval_value) = 0
               AND DAY(rs.start_date) = DAY(:recordDate)
           )
           OR
           (
               rs.type = 'YEAR'
               AND MOD(TIMESTAMPDIFF(YEAR, rs.start_date, :recordDate), rs.interval_value) = 0
               AND DAY(rs.start_date) = DAY(:recordDate)
               AND MONTH(rs.start_date) = MONTH(:recordDate)
           )
       GROUP BY
           cp.plan_id, cp.plan_name,
           rs.strategy_id, rs.type, rs.interval_value, rs.start_date,
           rw.repeat_week_id,
           cl.is_done
       ORDER BY
           cp.plan_id;
        """, nativeQuery = true)
    List<Object[]> findAllTodayByDate(@Param("recordDate") LocalDate recordDate);
}
