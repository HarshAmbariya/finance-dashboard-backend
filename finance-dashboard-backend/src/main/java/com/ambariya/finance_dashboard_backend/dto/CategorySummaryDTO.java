package com.ambariya.finance_dashboard_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySummaryDTO {

    private String category;
    private Double total;
}
