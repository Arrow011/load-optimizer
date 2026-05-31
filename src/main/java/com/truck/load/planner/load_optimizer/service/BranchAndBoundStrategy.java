package com.truck.load.planner.load_optimizer.service;
import com.truck.load.planner.load_optimizer.model.OptimizeResponse;
import com.truck.load.planner.load_optimizer.model.Truck;
import org.springframework.stereotype.Service;
import java.math.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.truck.load.planner.load_optimizer.model.Order;

@Service
public class BranchAndBoundStrategy
        implements OptimizationStrategy {

    private final ConstraintValidator validator;

    private BestSolution best;

    private long[] remainingPayout;

    public BranchAndBoundStrategy(
            ConstraintValidator validator) {

        this.validator = validator;
    }


    public OptimizeResponse optimize(Truck truck, List<Order> orders) {

        best = new BestSolution();

        orders.sort(
                Comparator.comparingLong(Order::payout_cents).reversed()
        );

        buildRemainingPayout(orders);

        dfs(
                0,
                orders,
                truck,
                new ArrayList<>(),
                0,
                0,
                0
        );

        return buildResponse(truck);
    }

    private void buildRemainingPayout(
            List<Order> orders) {

        remainingPayout =
                new long[orders.size() + 1];

        for (int i = orders.size() - 1;
             i >= 0;
             i--) {

            remainingPayout[i] =
                    remainingPayout[i + 1]
                            + orders.get(i)
                            .payout_cents();
        }
    }

    private void dfs(int index, List<Order> orders, Truck truck, List<Order> selected,
            long payout,
            long weight,
            long volume) {

        if (weight > truck.max_weight_lbs()) {
            return;
        }

        if (volume > truck.max_volume_cuft()) {
            return;
        }

        if (payout + remainingPayout[index] <= best.getPayout()) {
            return;
        }

        if (payout > best.getPayout()) {

            best.setPayout(payout);
            best.setWeight(weight);
            best.setVolume(volume);

            best.setOrders(
                    new ArrayList<>(selected)
            );
        }

        if (index == orders.size()) {
            return;
        }

        Order current = orders.get(index);

        boolean valid = validator.isRouteCompatible(selected, current) &&
                        validator.isTimeCompatible(current) &&
                        validator.isHazmatCompatible(selected, current);

        if (valid) {

            selected.add(current);

            dfs(
                    index + 1,
                    orders,
                    truck,
                    selected,
                    payout +
                            current.payout_cents(),
                    weight +
                            current.weight_lbs(),
                    volume +
                            current.volume_cuft()
            );

            selected.remove(
                    selected.size() - 1
            );
        }

        dfs(
                index + 1,
                orders,
                truck,
                selected,
                payout,
                weight,
                volume
        );
    }

    private OptimizeResponse buildResponse(
            Truck truck) {

        List<String> ids =
                best.getOrders()
                        .stream()
                        .map(Order::id)
                        .toList();

        return new OptimizeResponse(
                truck.id(),
                ids,
                best.getPayout(),
                best.getWeight(),
                best.getVolume(),

                roundToTwoDecimals(best.getWeight() * 100.0
                        / truck.max_weight_lbs()),

                roundToTwoDecimals(best.getVolume() * 100.0
                        / truck.max_volume_cuft())
        );
    }

    private double roundToTwoDecimals(double value) {

        return BigDecimal.valueOf(value)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}