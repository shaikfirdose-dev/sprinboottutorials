package com.project.springTest.SpringTest.repository;

import com.project.springTest.SpringTest.TestContainerConfiguration;
import com.project.springTest.SpringTest.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .name("John Doe")
                .email("shaik@gmail.com")
                .salary(50000L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        employeeRepository.findByEmail("");
        //given
        employeeRepository.save(employee);

        //when
        List<Employee> employees = employeeRepository.findByEmail(employee.getEmail());

        //then
        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmail()).isEqualTo("shaik@gmail.com");

    }

    @Test
    void testFindByEmail_whenEmailIsNotCorrect_thenReturnEmptyList() {
        //given
        employeeRepository.save(employee);

        //when
        List<Employee> employees = employeeRepository.findByEmail("jbvb@khshdk.com");

        //then
        assertThat(employees).isEmpty();

    }
}
