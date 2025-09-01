package com.firdose.week4RestClient.week4Tutorials.repository;

import com.firdose.week4RestClient.week4Tutorials.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
