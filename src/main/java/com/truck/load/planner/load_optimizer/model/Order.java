package com.truck.load.planner.load_optimizer.model;

import com.truck.load.planner.load_optimizer.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@ValidDateRange
public record Order(

        @NotBlank
        String id,

        @Positive
        long payout_cents,

        @Positive
        long weight_lbs,

        @Positive
        long volume_cuft,

        @NotBlank
        String origin,

        @NotBlank
        String destination,

        LocalDate pickup_date,

        LocalDate delivery_date,

        boolean is_hazmat

) {}
