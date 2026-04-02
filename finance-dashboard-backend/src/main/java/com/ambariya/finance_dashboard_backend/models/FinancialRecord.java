package com.ambariya.finance_dashboard_backend.models;

import com.ambariya.finance_dashboard_backend.dto.RecordType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "financial_records")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private RecordType type; // INCOME / EXPENSE

    private String category;

    private LocalDate date;

    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}