package com.ambariya.finance_dashboard_backend.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String name;
    private Role role;
    private Boolean active;
}