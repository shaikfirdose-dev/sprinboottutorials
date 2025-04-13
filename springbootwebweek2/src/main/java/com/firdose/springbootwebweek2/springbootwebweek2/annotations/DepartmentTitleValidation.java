package com.firdose.springbootwebweek2.springbootwebweek2.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DepartmentTitleValidator.class})
public @interface DepartmentTitleValidation {
    String message() default "Title should be in the range of [3,15]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
