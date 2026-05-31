package com.truck.load.planner.load_optimizer.service;

import com.truck.load.planner.load_optimizer.model.OptimizeResponse;
import com.truck.load.planner.load_optimizer.model.Order;
import com.truck.load.planner.load_optimizer.model.Truck;

import java.util.List;

public interface OptimizationStrategy {

    OptimizeResponse optimize(Truck truck, List<Order> orders);
}
