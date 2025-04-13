package com.firdose.springbootwebweek2.springbootwebweek2.controller;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.DepartmentDto;
import com.firdose.springbootwebweek2.springbootwebweek2.service.DepartmentService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable long departmentId){
        return new ResponseEntity<>(departmentService.getDepartmentById(departmentId), HttpStatus.OK);
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody @Valid DepartmentDto departmentDto, @PathVariable long departmentId){
        return new ResponseEntity<>(departmentService.updateDepartment(departmentDto, departmentId), HttpStatus.OK);
    }

    @PatchMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDto> partialDepartmentUpdate(@RequestBody Map<String, Object> updateDepartment, @PathVariable long departmentId){
        return new ResponseEntity<>(departmentService.partialDepartmentUpdate(updateDepartment, departmentId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable long departmentId){
        return new ResponseEntity<>(departmentService.deleteDepartment(departmentId), HttpStatus.OK);
    }
}
