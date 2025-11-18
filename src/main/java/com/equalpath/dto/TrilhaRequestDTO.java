package com.equalpath.dto;

import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusTrilha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrilhaRequestDTO(

        @NotBlank
        @Size(max = 150)
        String nome,

        @Size(max = 500)
        String descricao,

        @NotNull
        NivelTrilha nivel,

        @NotNull
        ObjetivoCarreira objetivo,

        @NotNull
        StatusTrilha status
) {}
