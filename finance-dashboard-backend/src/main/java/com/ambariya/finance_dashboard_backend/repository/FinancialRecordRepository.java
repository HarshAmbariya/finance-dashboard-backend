package com.ambariya.finance_dashboard_backend.repository;

import com.ambariya.finance_dashboard_backend.dto.RecordType;
import com.ambariya.finance_dashboard_backend.models.FinancialRecord;
import com.ambariya.finance_dashboard_backend.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByUser(Users user);

    List<FinancialRecord> findByUserAndType(Users user, RecordType type);

    List<FinancialRecord> findByUserAndCategory(Users user, String category);

    List<FinancialRecord> findByUserAndDateBetween(Users user, LocalDate start, LocalDate end);

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.user = :user AND r.type = 'INCOME'")
    Double getTotalIncome(Users user);

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.user = :user AND r.type = 'EXPENSE'")
    Double getTotalExpense(Users user);

    @Query("""
    SELECT r.category, SUM(r.amount)
    FROM FinancialRecord r
    WHERE r.user = :user
    GROUP BY r.category
""")
    List<Object[]> getCategorySummary(Users user);

    @Query("""
    SELECT MONTH(r.date), 
           SUM(CASE WHEN r.type = 'INCOME' THEN r.amount ELSE 0 END),
           SUM(CASE WHEN r.type = 'EXPENSE' THEN r.amount ELSE 0 END)
    FROM FinancialRecord r
    WHERE r.user = :user
    GROUP BY MONTH(r.date)
    ORDER BY MONTH(r.date)
""")
    List<Object[]> getMonthlySummary(Users user);

    @Query("""
    SELECT r FROM FinancialRecord r
    WHERE r.user = :user
    AND (:type IS NULL OR r.type = :type)
    AND (:category IS NULL OR r.category = :category)
    AND r.date >= COALESCE(:startDate, r.date)
    AND r.date <= COALESCE(:endDate, r.date)
""")
    Page<FinancialRecord> findRecordsWithFilters(
            @Param("user") Users user,
            @Param("type") RecordType type,
            @Param("category") String category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}
