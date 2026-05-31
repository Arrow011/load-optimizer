package com.truck.load.planner.load_optimizer.service;

import com.truck.load.planner.load_optimizer.model.Order;
import java.util.ArrayList;
import java.util.List;

public class BestSolution {

    private long payout;
    private long weight;
    private long volume;

    private List<Order> orders = new ArrayList<>();

    public long getPayout() {
        return payout;
    }

    public void setPayout(long payout) {
        this.payout = payout;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(
            List<Order> orders) {
        this.orders = orders;
    }
}
