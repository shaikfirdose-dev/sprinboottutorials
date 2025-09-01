package com.project.springTest.SpringTest.service;

import com.project.springTest.SpringTest.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employee);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto updateEmployee(Long id, EmployeeDto employee);
    void deleteEmployee(Long id);
}
