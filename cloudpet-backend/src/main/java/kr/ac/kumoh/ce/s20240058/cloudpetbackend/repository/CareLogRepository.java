package kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CareLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CareLogRepository extends JpaRepository<CareLog,Long> {
    CareLog findByCarePlan_PlanIdAndRecordDate(Long planId, LocalDate recordDate);
}
