package com.truck.load.planner.load_optimizer.service;
import org.springframework.stereotype.Component;
import java.util.List;
import com.truck.load.planner.load_optimizer.model.Order;
@Component
public class ConstraintValidator {

    public boolean isRouteCompatible(List<Order> selected, Order candidate) {

        if (selected.isEmpty()) {
            return true;
        }

        Order first = selected.get(0);

        return first.origin()
                .equals(candidate.origin())
                &&
                first.destination()
                        .equals(
                                candidate.destination()
                        );
    }

    public boolean isTimeCompatible(Order candidate) {

        return !candidate.pickup_date()
                .isAfter(
                        candidate.delivery_date()
                );
    }

    public boolean isHazmatCompatible(List<Order> selected, Order candidate) {

        return selected.stream()
                .allMatch(
                        order -> order.is_hazmat() == candidate.is_hazmat()
                );
    }
}
