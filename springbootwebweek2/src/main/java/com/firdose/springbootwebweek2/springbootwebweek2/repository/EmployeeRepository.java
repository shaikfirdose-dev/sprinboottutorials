package com.firdose.springbootwebweek2.springbootwebweek2.repository;

import com.firdose.springbootwebweek2.springbootwebweek2.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {


}
