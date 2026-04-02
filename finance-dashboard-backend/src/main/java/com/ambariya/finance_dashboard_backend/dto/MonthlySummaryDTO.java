package com.ambariya.finance_dashboard_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlySummaryDTO {

    private Integer month;
    private Double income;
    private Double expense;
}
