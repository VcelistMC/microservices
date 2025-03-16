package com.eazybytes.cards.validators;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
@Target( {ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumber {
    String message() default "Invalid phone number, should be 11 digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return s != null && s.matches("(^$|[0-9]{11})");
    }
}
