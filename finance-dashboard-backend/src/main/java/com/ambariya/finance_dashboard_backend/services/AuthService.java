package com.ambariya.finance_dashboard_backend.services;


import com.ambariya.finance_dashboard_backend.dto.AuthResponse;
import com.ambariya.finance_dashboard_backend.dto.LoginRequest;
import com.ambariya.finance_dashboard_backend.dto.RegisterRequest;
import com.ambariya.finance_dashboard_backend.dto.Role;
import com.ambariya.finance_dashboard_backend.exception.UserAlreadyExistsException;
import com.ambariya.finance_dashboard_backend.repository.UserRepository;
import com.ambariya.finance_dashboard_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import com.ambariya.finance_dashboard_backend.models.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse registerUser(RegisterRequest request, Role role) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already registered with this email");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return AuthResponse.builder()
                .message(role + " registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}