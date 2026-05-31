package com.truck.load.planner.load_optimizer.validation;

import com.truck.load.planner.load_optimizer.exception.PayloadTooLargeException;
import com.truck.load.planner.load_optimizer.model.OptimizeRequest;
import com.truck.load.planner.load_optimizer.model.OptimizeResponse;
import com.truck.load.planner.load_optimizer.service.OptimizationStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LoadOptimizationService {

    private final OptimizationStrategy strategy;
    private static final int MAX_SUPPORTED_ORDERS = 22;

    public LoadOptimizationService(OptimizationStrategy strategy) {

        this.strategy = strategy;
    }

    @Cacheable(
            value = "optimization-cache",
            key = "@cacheKeyGenerator.generate(#request)"
    )
    public OptimizeResponse optimize(OptimizeRequest request) {
        if (request.orders().size() > MAX_SUPPORTED_ORDERS) {
            throw new PayloadTooLargeException("Maximum supported orders is "
                            + MAX_SUPPORTED_ORDERS);
        }

        return strategy.optimize(request.truck(), request.orders());
    }
}
