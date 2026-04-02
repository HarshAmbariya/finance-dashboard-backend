package com.ambariya.finance_dashboard_backend.services;


import com.ambariya.finance_dashboard_backend.dto.UserResponseDTO;
import com.ambariya.finance_dashboard_backend.dto.UserUpdateDTO;
import com.ambariya.finance_dashboard_backend.models.Users;
import com.ambariya.finance_dashboard_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        Users user = getUserEntity(id);
        return mapToDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        Users user = getUserEntity(id);

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getRole() != null) user.setRole(dto.getRole());
        if (dto.getActive() != null) user.setActive(dto.getActive());

        return mapToDTO(userRepository.save(user));
    }

    public void updateUserStatus(Long id, boolean active) {
        Users user = getUserEntity(id);
        user.setActive(active);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponseDTO getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    private Users getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserResponseDTO mapToDTO(Users user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
}