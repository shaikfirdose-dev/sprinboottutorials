package com.firdose.springbootwebweek2.springbootwebweek2.dto;

import com.firdose.springbootwebweek2.springbootwebweek2.annotations.EmployeeAgeValidation;
import com.firdose.springbootwebweek2.springbootwebweek2.annotations.EmployeeValidation;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.DepartmentEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private long id;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 10, message = "Number of characters should be in the range : [3 ,10]")
    private String name;

    @Email(message = "Email is not valid")
    private String email;

//    @Pattern(regexp = "^(ADMIN|USER)$")
//    @EmployeeValidation
//    private String role;

//    @Max(value = 80, message = "Maximum age should be 80 years")
//    @Min(value = 18, message = "Minimum age should be 18 years")
//    @Positive(message = "Age should be positive")
    @EmployeeAgeValidation
    private Integer age;

//    @Past(message = "Date should be past")
//    @PastOrPresent(message = "Date should be past or present")
    @FutureOrPresent(message = "Date should be future or present")
    private LocalDate dateOfJoining;

//    @Digits(integer = 6, fraction = 2, message = "The salary can be in the form of XXXX.XX")
//    @DecimalMin(value = "100.12")
//    @DecimalMax(value = "100000.50")
//    private Double salary;

    @AssertTrue(message = "Status should be active")
    private Boolean active;

    private DepartmentEntity department;

    private DepartmentEntity workerDepartment;

    private Set<DepartmentEntity> freelanceDepartments;
}
