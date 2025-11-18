package com.equalpath.dto;

import com.equalpath.domain.enums.ObjetivoCarreira;
import jakarta.validation.constraints.*;

public record UsuarioRequestDTO(
        @NotBlank @Size(max = 100) String nome,
        @Size(max = 100) String sobrenome,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(max = 20) String telefone,
        @Size(max = 2) String estado,
        ObjetivoCarreira objetivoCarreira
) {}
