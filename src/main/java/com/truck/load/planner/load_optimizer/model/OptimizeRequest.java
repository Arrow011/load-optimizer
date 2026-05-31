package com.truck.load.planner.load_optimizer.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OptimizeRequest(

        @NotNull
        @Valid
        Truck truck,

        @NotEmpty
        List<@Valid Order> orders

) {}
