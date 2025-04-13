package com.firdose.springbootwebweek2.springbootwebweek2.service;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.EmployeeDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.EmployeeEntity;
import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.ResourceNotFoundException;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public List<EmployeeDto> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto){
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    public Optional<EmployeeDto> getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class));
    }

    public Boolean isExistsEmployeeById(long id){
        boolean isExists = employeeRepository.existsById(id);
        if(!isExists){
            throw new ResourceNotFoundException("Resourse Not Found with id:"+id);
        }
        return isExists;
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
        isExistsEmployeeById(id);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    public EmployeeDto partialUpdateEmployee(Map<String, Object> updates, long id) {
        isExistsEmployeeById(id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        updates.forEach((field, value) -> {
            Field fieldsToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldsToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldsToBeUpdated, employeeEntity, value);
        });

        return modelMapper.map(employeeEntity, EmployeeDto.class);
    }

    public Boolean deleteEmployee(long employeeId) {
        isExistsEmployeeById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }
}
