package com.ambariya.finance_dashboard_backend.controllers;


import com.ambariya.finance_dashboard_backend.dto.AuthResponse;
import com.ambariya.finance_dashboard_backend.dto.LoginRequest;
import com.ambariya.finance_dashboard_backend.dto.RegisterRequest;
import com.ambariya.finance_dashboard_backend.dto.Role;
import com.ambariya.finance_dashboard_backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Default registration → VIEWER
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerViewer(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request, Role.VIEWER));
    }

    // Analyst registration
    @PostMapping("/register/analyst")
    public ResponseEntity<AuthResponse> registerAnalyst(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request, Role.ANALYST));
    }

    // Admin creation (protected)
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request, Role.ADMIN));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}