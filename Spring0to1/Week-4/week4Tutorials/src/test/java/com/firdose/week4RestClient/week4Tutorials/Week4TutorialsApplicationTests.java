package com.firdose.week4RestClient.week4Tutorials;

import com.firdose.week4RestClient.week4Tutorials.client.EmployeeClient;
import com.firdose.week4RestClient.week4Tutorials.dto.EmployeeDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Week4TutorialsApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	@Order(value = 3)
	void getAllEmployees(){
		List<EmployeeDto> employees = employeeClient.getAllEmployees();
		System.out.println(employees);
	}

	@Test
	@Order(value = 2)
	void getEmployeeById(){
		EmployeeDto employeeDto = employeeClient.getEmployeeById(1L);
		System.out.println(employeeDto);
	}

	@Test
	@Order(value = 1)
	void createEmployee(){
		EmployeeDto employee = new EmployeeDto(0,"shaik","shaik@gmail.com",22, LocalDate.of(2025, 12,25), true);
		EmployeeDto employeeDto = employeeClient.createEmployee(employee);
		System.out.println(employeeDto);
	}




}
