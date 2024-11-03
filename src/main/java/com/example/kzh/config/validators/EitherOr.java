package com.example.kzh.config.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EitherOrValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EitherOr {
    String message() default "Either questionGenerate or questionCreate must be provided, but not both";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
