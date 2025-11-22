package com.equalpath.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(max = 100)
        String sobrenome,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        @Size(max = 20)
        String telefone,

        @NotBlank
        @Size(max = 2)
        String estado,

        @NotBlank
        @Size(max = 50)
        String objetivoCarreira,

        @NotBlank
        @Size(max = 50)
        String statusPerfil
) {}
