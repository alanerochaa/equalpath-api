package com.equalpath.dto;

import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusPerfil;

import java.time.LocalDate;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String sobrenome,
        String email,
        String telefone,
        String estado,
        LocalDate dtCadastro,
        ObjetivoCarreira objetivoCarreira,
        StatusPerfil statusPerfil
) {}
