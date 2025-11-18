package com.equalpath.dto;

import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusPerfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(max = 200)
        String sobrenome,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String telefone,

        @Size(max = 2)
        String estado,

        @NotNull
        ObjetivoCarreira objetivoCarreira,

        @NotNull
        StatusPerfil statusPerfil
) {}
