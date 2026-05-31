package com.truck.load.planner.load_optimizer.validation;

import com.truck.load.planner.load_optimizer.model.Order;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Order> {

    @Override
    public boolean isValid(Order order, ConstraintValidatorContext context) {

        if (order == null) {
            return true;
        }

        return !order.pickup_date()
                .isAfter(
                        order.delivery_date()
                );
    }
}
