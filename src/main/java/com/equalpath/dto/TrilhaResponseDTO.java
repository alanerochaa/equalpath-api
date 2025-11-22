package com.equalpath.dto;

import java.time.LocalDate;

public record TrilhaResponseDTO(
        Long id,
        String nome,
        String descricao,
        String nivel,
        String objetivo,
        String status,
        LocalDate dtCriacao
) {}
