package com.project.springTest.SpringTest.service.impl;

import com.project.springTest.SpringTest.dto.EmployeeDto;
import com.project.springTest.SpringTest.entity.Employee;
import com.project.springTest.SpringTest.exception.ResourceNotFoundException;
import com.project.springTest.SpringTest.repository.EmployeeRepository;
import com.project.springTest.SpringTest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private static final String CACHE_NAME = "employees";

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.info("Creating employee with details: {}", employeeDto);
        List<Employee> employees = employeeRepository.findByEmail(employeeDto.getEmail());
        if (!employees.isEmpty()) {
            log.error("Employee with email {} already exists", employeeDto.getEmail());
            throw new ResourceNotFoundException("Employee with this email already exists." + employeeDto.getEmail());
        }
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        log.info("Employee created successfully with ID: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME)
    public List<EmployeeDto> getAllEmployees() {
        log.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        log.info("Total employees fetched: {}", employees.size());
        if (!employees.isEmpty()) {
            return employees.stream()
                    .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                    .toList();
        }
        log.warn("No employees found");
        return List.of();
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        log.info("Employee found: {}", employee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        if (!existingEmployee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempeted to change email for employee with ID: {}", id);
            throw new RuntimeException("The email of the employee cannot be changed.");
        }
        employeeDto.setId(existingEmployee.getId());
        modelMapper.map(employeeDto, existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        log.info("Employee updated successfully with ID: {}", updatedEmployee.getId());
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employee);
        log.info("Employee deleted successfully with ID: {}", id);
        return;
    }
}
