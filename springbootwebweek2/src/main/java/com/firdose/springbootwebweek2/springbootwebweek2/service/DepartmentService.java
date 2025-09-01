package com.firdose.springbootwebweek2.springbootwebweek2.service;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.DepartmentDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.DepartmentEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.EmployeeEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.DepartmentNotFoundException;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.DepartmentRepository;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.EmployeeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        log.trace("Trying to convert DepartmentDto to DepartmentEntity");
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        log.trace("Trying to save departmentEntity in DB");
        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
        log.info("Saved Department into DB : {}",savedDepartment);
        return modelMapper.map(savedDepartment, DepartmentDto.class);

    }

    public List<DepartmentDto> getAllDepartments() {
        log.trace("Trying to get all Departments");
        List<DepartmentEntity> departments = departmentRepository.findAll();

        log.debug("All departments : {}", departments);
        log.trace("Trying to convert all Department Entities into Department Dtos");
        return departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, long departmentId) {
        log.trace("Trying to update department");
        isDepartmentExistsById(departmentId);
        log.debug("Department Id exists: {} ",departmentId);
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        log.trace("Setting departmentId");
        departmentEntity.setId(departmentId);
        log.trace("Trying to update department in to DB");
        DepartmentEntity updatedDepartment = departmentRepository.save(departmentEntity);
        log.info("Department has been updated : {}",updatedDepartment);
        log.info("Trying to convert Entity to DTO");
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
        log.trace("Trying to check departmentId exists or not");
        boolean exists = departmentRepository.existsById(departmentId);
        if(!exists){
            log.error("Department id not exists : {}", departmentId);
            throw new DepartmentNotFoundException("Department not found exception with id:"+departmentId);
        }
        log.info("Department id exists in the db");
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
        log.trace("Trying to perform assignManagerToDepartment");
        Optional<DepartmentEntity> savedDepartment = departmentRepository.findById(departmentId);
        log.info("Fetched department : {}", savedDepartment);
        Optional<EmployeeEntity> savedEmployee = employeeRepository.findById(employeeId);
        log.info("Fetched employee entity : {}", savedEmployee);


        log.trace("Assigning manager to the department");
        return savedDepartment.flatMap(
                department -> savedEmployee.map(
                        employee -> {
                            log.info("Assigned manager tom the department");
                            department.setManager(employee);
                            DepartmentEntity departmentEntity = departmentRepository.save(department);
                            log.debug("Department has been saved in the DB : "+departmentEntity);
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
