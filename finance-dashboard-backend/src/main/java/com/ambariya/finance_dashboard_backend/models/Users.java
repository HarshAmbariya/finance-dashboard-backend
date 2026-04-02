package com.ambariya.finance_dashboard_backend.models;

import com.ambariya.finance_dashboard_backend.dto.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;
}