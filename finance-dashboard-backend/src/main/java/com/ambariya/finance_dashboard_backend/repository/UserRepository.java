package com.ambariya.finance_dashboard_backend.repository;

import com.ambariya.finance_dashboard_backend.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Page<Users> findByActiveTrue(Pageable pageable);
}