package com.ambariya.finance_dashboard_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;
}
