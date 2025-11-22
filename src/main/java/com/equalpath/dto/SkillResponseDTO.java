package com.equalpath.dto;

import java.time.LocalDate;

public record SkillResponseDTO(
        Long id,
        String nome,
        String descricao,
        String nivel,
        String categoria,
        String tipo,
        LocalDate ultimoAcesso
) {}
