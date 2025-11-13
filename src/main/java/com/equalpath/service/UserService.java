package com.equalpath.service;

import com.equalpath.domain.User;
import com.equalpath.domain.enums.UserRole;
import com.equalpath.dto.RegisterUserDTO;
import com.equalpath.dto.UserResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO register(RegisterUserDTO dto) {
        User user = User.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .localizacao(dto.getLocalizacao())
                .areaInteresse(dto.getAreaInteresse())
                .role(UserRole.CANDIDATE)
                .build();

        userRepository.save(user);

        return toResponse(user);
    }

    public UserResponseDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toResponse(user);
    }

    private UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .localizacao(user.getLocalizacao())
                .areaInteresse(user.getAreaInteresse())
                .role(user.getRole().name())
                .build();
    }
}
