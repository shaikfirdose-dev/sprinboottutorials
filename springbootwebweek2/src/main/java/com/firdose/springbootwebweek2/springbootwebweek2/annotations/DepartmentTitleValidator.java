package com.firdose.springbootwebweek2.springbootwebweek2.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentTitleValidator implements ConstraintValidator<DepartmentTitleValidation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.length()>=3 && s.length()<=15){
            return true;
        }
        return false;
    }
}
