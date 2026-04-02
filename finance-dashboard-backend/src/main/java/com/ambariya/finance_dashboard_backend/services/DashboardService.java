package com.ambariya.finance_dashboard_backend.services;

import com.ambariya.finance_dashboard_backend.dto.CategorySummaryDTO;
import com.ambariya.finance_dashboard_backend.dto.DashboardSummaryDTO;
import com.ambariya.finance_dashboard_backend.dto.MonthlySummaryDTO;
import com.ambariya.finance_dashboard_backend.models.Users;
import com.ambariya.finance_dashboard_backend.repository.FinancialRecordRepository;
import com.ambariya.finance_dashboard_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public DashboardSummaryDTO getSummary() {
        Users user = getCurrentUser();

        Double income = recordRepository.getTotalIncome(user);
        Double expense = recordRepository.getTotalExpense(user);

        income = (income != null) ? income : 0;
        expense = (expense != null) ? expense : 0;

        return new DashboardSummaryDTO(
                income,
                expense,
                income - expense
        );
    }

    public List<CategorySummaryDTO> getCategorySummary() {
        Users user = getCurrentUser();

        return recordRepository.getCategorySummary(user)
                .stream()
                .map(obj -> new CategorySummaryDTO(
                        (String) obj[0],
                        (Double) obj[1]
                ))
                .toList();
    }

    public List<MonthlySummaryDTO> getMonthlySummary() {
        Users user = getCurrentUser();

        return recordRepository.getMonthlySummary(user)
                .stream()
                .map(obj -> new MonthlySummaryDTO(
                        (Integer) obj[0],
                        (Double) obj[1],
                        (Double) obj[2]
                ))
                .toList();
    }

    private Users getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
