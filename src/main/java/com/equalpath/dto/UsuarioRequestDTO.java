package com.equalpath.dto;

import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusPerfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @NotBlank(message = "O nome não pode ser vazio.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        @NotBlank(message = "O sobrenome não pode ser vazio.")
        @Size(max = 200, message = "O sobrenome deve ter no máximo 200 caracteres.")
        String sobrenome,

        @NotBlank(message = "O email não pode ser vazio.")
        @Email(message = "Formato de email inválido.")
        @Size(max = 150, message = "O email deve ter no máximo 150 caracteres.")
        String email,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String telefone,

        @Size(max = 2, message = "O estado deve ter no máximo 2 caracteres (UF).")
        String estado,

        @NotNull(message = "O objetivo de carreira é obrigatório.")
        ObjetivoCarreira objetivoCarreira,

        @NotNull(message = "O status do perfil é obrigatório.")
        StatusPerfil statusPerfil
) {}
