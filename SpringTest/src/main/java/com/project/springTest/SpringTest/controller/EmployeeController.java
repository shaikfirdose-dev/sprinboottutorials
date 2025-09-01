package com.project.springTest.SpringTest.controller;

import com.project.springTest.SpringTest.dto.EmployeeDto;
import com.project.springTest.SpringTest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(path = "/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody  EmployeeDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);

    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }




}
