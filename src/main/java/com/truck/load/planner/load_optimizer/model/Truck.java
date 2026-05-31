package com.truck.load.planner.load_optimizer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record Truck(
        @NotBlank
        String id,

        @Positive
        long max_weight_lbs,

        @Positive
        long max_volume_cuft) {
}
