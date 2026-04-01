package com.ambariya.finance_dashboard_backend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    // 👤 VIEWER, ANALYST, ADMIN → can view
    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER', 'ANALYST', 'ADMIN')")
    public String getTransactions() {
        return "All transactions";
    }

    // 📊 ANALYST, ADMIN → can create
    @PostMapping
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    public String createTransaction() {
        return "Transaction created";
    }

    // 🛠️ ADMIN only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTransaction() {
        return "Transaction deleted";
    }
}