package com.equalpath.dto;

import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusTrilha;

import java.time.LocalDate;

public record TrilhaResponseDTO(
        Long id,
        String nome,
        String descricao,
        NivelTrilha nivel,
        ObjetivoCarreira objetivo,
        StatusTrilha status,
        LocalDate dtCriacao
) {}
