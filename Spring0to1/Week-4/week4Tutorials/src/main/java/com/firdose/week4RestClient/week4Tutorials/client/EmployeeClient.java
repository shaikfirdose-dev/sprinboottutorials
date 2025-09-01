package com.firdose.week4RestClient.week4Tutorials.client;

import com.firdose.week4RestClient.week4Tutorials.dto.EmployeeDto;

import java.util.List;

public interface EmployeeClient {


    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto createEmployee(EmployeeDto employeeDto);
}
