package com.ambariya.finance_dashboard_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordDTO {

    private Long id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String note;
}
