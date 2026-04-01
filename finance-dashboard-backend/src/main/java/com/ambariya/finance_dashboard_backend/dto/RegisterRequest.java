package com.ambariya.finance_dashboard_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
}