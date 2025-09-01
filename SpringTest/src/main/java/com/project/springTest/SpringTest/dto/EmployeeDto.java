package com.project.springTest.SpringTest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private Long salary;
}
