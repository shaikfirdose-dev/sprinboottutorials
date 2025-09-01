package com.project.springTest.SpringTest.service;


import com.project.springTest.SpringTest.TestContainerConfiguration;
import com.project.springTest.SpringTest.dto.EmployeeDto;
import com.project.springTest.SpringTest.entity.Employee;
import com.project.springTest.SpringTest.exception.ResourceNotFoundException;
import com.project.springTest.SpringTest.repository.EmployeeRepository;
import com.project.springTest.SpringTest.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
@Import(TestContainerConfiguration.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .email("shaik@gmail.com")
                .name("Shaik")
                .salary(2000L)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
    }


    @Test
    void testFindById_whenIdIsPresent_thenReturnEmployee() {
        //assign
        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));

        //action
        EmployeeDto employeeDto = employeeService.getEmployeeById(mockEmployee.getId());

        //assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(mockEmployee.getId());
        verify(employeeRepository, atMost(2)).findById(mockEmployee.getId());

    }

    @Test
    void testFindById_whenIdIsNotPresent_thenThrowAnException() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        //assert and act

        assertThatThrownBy(() -> employeeService.getEmployeeById(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with ID: " + 2);

        verify(employeeRepository).findById(2L);
    }

    @Test
    void testFindById_whenIdIsNotPresent_thenThrowException() {
        Long id = 2L;
        //assign
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        //action & assert
        try {
            employeeService.getEmployeeById(id);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).contains("Employee not found with ID: " + id);
        }

        verify(employeeRepository, atMost(1)).findById(id);
    }


    @Test
    void testCreateEmployee_whenEmployeeIsValid_thenReturnCreatedEmployee() {
        //given
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        //when
        EmployeeDto employeeDto = employeeService.createEmployee(mockEmployeeDto);

        //then
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
        verify(employeeRepository, times(1)).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException() {

        //arrange
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of(mockEmployee));

        //act and assert

        assertThatThrownBy(() -> employeeService.createEmployee(mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee with this email already exists." + mockEmployeeDto.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testGetAllEmployees_whenEmployeesExist_thenReturnListOfEmployees() {
        //arrange
        when(employeeRepository.findAll()).thenReturn(List.of(mockEmployee));

        //act
        List<EmployeeDto> employees = employeeService.getAllEmployees();

        //assert
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmployees_whenNoEmployeesExist_thenReturnEmptyList() {
        //arrange
        when(employeeRepository.findAll()).thenReturn(List.of());

        //act
        List<EmployeeDto> employees = employeeService.getAllEmployees();

        //assert
        assertThat(employees).isEmpty();
        verify(employeeRepository).findAll();
    }

    @Test
    void testUpdateEmployee_whenEmployeeExists_thenReturnUpdatedEmployee() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
//        mockEmployeeDto.setName("Firdose");

        //act
        EmployeeDto updatedEmployee = employeeService.updateEmployee(mockEmployee.getId(), mockEmployeeDto);

        //assert
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, times(1)).findById(mockEmployee.getId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmployeeWithIdDoesNotExist_thenThrowException() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThatThrownBy(() -> employeeService.updateEmployee(20L, mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with ID: " + 20);

        verify(employeeRepository).findById(20L);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmployeeWithDifferentEmail_thenThrowException() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setEmail("Firdose@gmail.com");

        //act and assert
        assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployee.getId(), mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be changed.");

        verify(employeeRepository).findById(anyLong());
    }


    @Test
    void testDeleteEmployee_whenAttemptingToDeleteEmployeeWithValidEmployeeId_thenDeleteEmployee() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));

        //act
        employeeService.deleteEmployee(mockEmployee.getId());

        //assert
        verify(employeeRepository).findById(mockEmployee.getId());
        verify(employeeRepository).delete(mockEmployee);
    }

    @Test
    void testDeleteEmployee_whenAttemptingToDeleteEmployeeWithInvalidEmployeeId_thenThrowException() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThatThrownBy(() -> employeeService.deleteEmployee(20L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with ID: " + 20);

        verify(employeeRepository).findById(20L);
        verify(employeeRepository, never()).delete(any());
    }

}
