package kr.ac.kumoh.ce.s20240058.cloudpetbackend.repository;

import kr.ac.kumoh.ce.s20240058.cloudpetbackend.domain.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarePlanRepository extends JpaRepository<CarePlan,Long> {
}
