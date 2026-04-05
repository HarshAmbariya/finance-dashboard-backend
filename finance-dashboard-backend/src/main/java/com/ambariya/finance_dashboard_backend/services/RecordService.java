package com.ambariya.finance_dashboard_backend.services;

import com.ambariya.finance_dashboard_backend.dto.PageResponse;
import com.ambariya.finance_dashboard_backend.dto.RecordDTO;
import com.ambariya.finance_dashboard_backend.dto.RecordType;
import com.ambariya.finance_dashboard_backend.models.FinancialRecord;
import com.ambariya.finance_dashboard_backend.models.Users;
import com.ambariya.finance_dashboard_backend.repository.FinancialRecordRepository;
import com.ambariya.finance_dashboard_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public RecordDTO createRecord(RecordDTO dto) {
        Users user = getCurrentUserEntity();

        FinancialRecord record = FinancialRecord.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .category(dto.getCategory())
                .date(dto.getDate())
                .note(dto.getNote())
                .user(user)
                .build();

        return mapToDTO(recordRepository.save(record));
    }

    public PageResponse<RecordDTO> getRecords(RecordType type,
                                              String category,
                                              LocalDate startDate,
                                              LocalDate endDate,
                                              Pageable pageable) {

        Users user = getCurrentUserEntity();

        Page<FinancialRecord> records = recordRepository.findRecordsWithFilters(
                user, type, category, startDate, endDate, pageable
        );

        Page<RecordDTO> dtoPage = records.map(this::mapToDTO);

        return new PageResponse<>(dtoPage);
    }

    public RecordDTO updateRecord(Long id, RecordDTO dto) {
        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNote(dto.getNote());

        return mapToDTO(recordRepository.save(record));
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }


    private Users getCurrentUserEntity() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private RecordDTO mapToDTO(FinancialRecord record) {
        RecordDTO dto = new RecordDTO();
        dto.setId(record.getId());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setNote(record.getNote());
        return dto;
    }
}