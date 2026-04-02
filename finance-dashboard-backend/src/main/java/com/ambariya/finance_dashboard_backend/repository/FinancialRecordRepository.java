package com.ambariya.finance_dashboard_backend.repository;

import com.ambariya.finance_dashboard_backend.dto.RecordType;
import com.ambariya.finance_dashboard_backend.models.FinancialRecord;
import com.ambariya.finance_dashboard_backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByUser(Users user);

    List<FinancialRecord> findByUserAndType(Users user, RecordType type);

    List<FinancialRecord> findByUserAndCategory(Users user, String category);

    List<FinancialRecord> findByUserAndDateBetween(Users user, LocalDate start, LocalDate end);
}
