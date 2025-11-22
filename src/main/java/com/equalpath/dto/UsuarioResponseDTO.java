package com.equalpath.dto;

import java.time.LocalDate;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String sobrenome,
        String email,
        String telefone,
        String estado,
        LocalDate dtCadastro,
        String objetivoCarreira,
        String statusPerfil
) {}
