package com.project.springTest.SpringTest.repository;

import com.project.springTest.SpringTest.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByEmail(String email);
}
