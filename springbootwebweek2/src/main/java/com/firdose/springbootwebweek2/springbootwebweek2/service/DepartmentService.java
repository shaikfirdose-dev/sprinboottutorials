package com.firdose.springbootwebweek2.springbootwebweek2.service;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.DepartmentDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.DepartmentEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.DepartmentNotFoundException;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
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
}
