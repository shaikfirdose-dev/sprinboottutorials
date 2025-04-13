package com.firdose.springbootwebweek2.springbootwebweek2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.firdose.springbootwebweek2.springbootwebweek2.annotations.DepartmentTitleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private long id;

    @NotNull(message = "Title should not be null")
    @NotBlank(message = "Title should not be blank")
    @DepartmentTitleValidation
    private String title;

    @Min(value = 10, message = "Minimum department capacity should be 10")
    @Max(value = 50, message = "Maximum department capacity should be 50")
    private Integer departmentCapacity;

    @Past
    private LocalDate departmentCreated;
    @AssertTrue(message = "Department should be active")
    private Boolean isActive;
}
