package com.ambariya.finance_dashboard_backend.controllers;

import com.ambariya.finance_dashboard_backend.dto.CategorySummaryDTO;
import com.ambariya.finance_dashboard_backend.dto.DashboardSummaryDTO;
import com.ambariya.finance_dashboard_backend.dto.MonthlySummaryDTO;
import com.ambariya.finance_dashboard_backend.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategorySummaryDTO>> getCategorySummary() {
        return ResponseEntity.ok(dashboardService.getCategorySummary());
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<MonthlySummaryDTO>> getMonthlySummary() {
        return ResponseEntity.ok(dashboardService.getMonthlySummary());
    }
}
