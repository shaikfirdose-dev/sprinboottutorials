package com.firdose.springbootwebweek2.springbootwebweek2.repository;

import com.firdose.springbootwebweek2.springbootwebweek2.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
