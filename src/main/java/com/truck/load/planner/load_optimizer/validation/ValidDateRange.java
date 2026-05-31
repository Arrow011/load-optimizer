package com.truck.load.planner.load_optimizer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(
        validatedBy =
                DateRangeValidator.class
)
public @interface ValidDateRange {
    String message() default "pickup_date must be before delivery_date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
