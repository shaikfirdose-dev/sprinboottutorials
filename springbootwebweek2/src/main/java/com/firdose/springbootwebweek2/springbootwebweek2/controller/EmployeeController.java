package com.firdose.springbootwebweek2.springbootwebweek2.controller;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.EmployeeDto;
import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.ResourceNotFoundException;
import com.firdose.springbootwebweek2.springbootwebweek2.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/")
    public ResponseEntity<List<EmployeeDto>> getEmployees(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }


//    @PostMapping(path = "/")
//    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee){
//        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
//    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable long id){
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto, id));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> partialEmployeeUpdate(@RequestBody Map<String, Object> employeeDto, @PathVariable long id){
        return ResponseEntity.ok(employeeService.partialUpdateEmployee(employeeDto, id));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable long employeeId){
        return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long employeeId){
        Optional<EmployeeDto> employeeDto= employeeService.getEmployeeById(employeeId);
        return employeeDto
                .map(emp -> ResponseEntity.ok(emp))
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with this id :"+employeeId));
    }

    @PostMapping(path = "/")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }



}
