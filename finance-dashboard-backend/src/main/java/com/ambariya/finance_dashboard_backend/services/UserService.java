package com.ambariya.finance_dashboard_backend.services;


import com.ambariya.finance_dashboard_backend.dto.PageResponse;
import com.ambariya.finance_dashboard_backend.dto.UserResponseDTO;
import com.ambariya.finance_dashboard_backend.dto.UserUpdateDTO;
import com.ambariya.finance_dashboard_backend.models.Users;
import com.ambariya.finance_dashboard_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public PageResponse<UserResponseDTO> getAllUsers(Pageable pageable) {

        Page<UserResponseDTO> page = userRepository.findByActiveTrue(pageable)
                .map(this::mapToDTO);

        return new PageResponse<>(page);
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
        Users user = getUserEntity(id);

        user.setActive(false);
        userRepository.save(user);
    }

    public UserResponseDTO getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User is deactivated");
        }

        return mapToDTO(user);
    }

    private Users getUserEntity(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User is deactivated");
        }

        return user;
    }

    private UserResponseDTO mapToDTO(Users user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}