package com.ambariya.finance_dashboard_backend.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private boolean active;
}