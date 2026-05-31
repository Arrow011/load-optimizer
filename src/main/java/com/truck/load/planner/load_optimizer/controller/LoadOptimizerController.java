package com.truck.load.planner.load_optimizer.controller;

import com.truck.load.planner.load_optimizer.model.OptimizeRequest;
import com.truck.load.planner.load_optimizer.model.OptimizeResponse;
import com.truck.load.planner.load_optimizer.validation.LoadOptimizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/load-optimizer")
@RequiredArgsConstructor
public class LoadOptimizerController {
    private final LoadOptimizationService loadOptimizationService;

    @PostMapping("/optimize")
    public ResponseEntity<OptimizeResponse> optimize(@Valid @RequestBody OptimizeRequest request) {

        return ResponseEntity.ok(
                loadOptimizationService.optimize(request)
        );
    }
}
