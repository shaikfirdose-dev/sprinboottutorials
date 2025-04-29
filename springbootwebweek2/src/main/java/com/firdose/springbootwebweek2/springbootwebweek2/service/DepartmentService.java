package com.firdose.springbootwebweek2.springbootwebweek2.service;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.DepartmentDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.DepartmentEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.EmployeeEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.DepartmentNotFoundException;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.DepartmentRepository;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
        return modelMapper.map(savedDepartment, DepartmentDto.class);
    }

    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentEntity> departments = departmentRepository.findAll();

        return departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, long departmentId) {
        isDepartmentExistsById(departmentId);
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        departmentEntity.setId(departmentId);
        DepartmentEntity updatedDepartment = departmentRepository.save(departmentEntity);
        return modelMapper.map(updatedDepartment, DepartmentDto.class);
    }

    public Boolean deleteDepartment(long departmentId) {
        isDepartmentExistsById(departmentId);
        departmentRepository.deleteById(departmentId);
        return true;
    }

    public DepartmentDto getDepartmentById(long departmentId) {
        isDepartmentExistsById(departmentId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        return modelMapper.map(departmentEntity, DepartmentDto.class);
    }


    private boolean isDepartmentExistsById(long departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);
        if(!exists){
            throw new DepartmentNotFoundException("Department not found exception with id:"+departmentId);
        }
        return true;
    }

    public DepartmentDto partialDepartmentUpdate(Map<String, Object> updateDepartment, long departmentId) {
        isDepartmentExistsById(departmentId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        updateDepartment.forEach((field, value) -> {
            Field fieldTobeUpdated = ReflectionUtils.findRequiredField(DepartmentEntity.class, field);
            fieldTobeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldTobeUpdated, departmentEntity, value);
        });

        return modelMapper.map(departmentEntity, DepartmentDto.class);
    }

    public DepartmentEntity assignManagerToDepartment(long departmentId, long employeeId) {
        Optional<DepartmentEntity> savedDepartment = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> savedEmployee = employeeRepository.findById(employeeId);

        return savedDepartment.flatMap(
                department -> savedEmployee.map(
                        employee -> {
                            department.setManager(employee);
                            DepartmentEntity departmentEntity = departmentRepository.save(department);
                            return department;
                        }
                )
        ).orElse(null);
    }

    public DepartmentEntity assignWorkersToDepartment(long departmentId, long employeeId) {
        Optional<DepartmentEntity> savedDepartment = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> savedEmployee = employeeRepository.findById(employeeId);

        return savedDepartment.flatMap(
                department -> savedEmployee.map(
                        employee -> {
                            employee.setWorkerDepartment(department);
                            employeeRepository.save(employee);
                            department.getWorkers().add(employee);
                            return department;
                        }
                )
        ).orElse(null);
    }

    public DepartmentEntity assignFreelancerToDepartment(long departmentId, long employeeId) {
        Optional<DepartmentEntity> savedDepartment = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> savedEmployee = employeeRepository.findById(employeeId);

        return savedDepartment.flatMap(
                department -> savedEmployee.map(
                        employee -> {
                            employee.getFreelanceDepartments().add(department);
                            employeeRepository.save(employee);
                            department.getFreelancers().add(employee);
                            return department;
                        }
                )
        ).orElse(null);
    }
}
