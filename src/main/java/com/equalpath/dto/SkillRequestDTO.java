package com.equalpath.dto;

import com.equalpath.domain.enums.CategoriaSkill;
import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.TipoSkill;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SkillRequestDTO(

        @NotBlank
        @Size(max = 100)
        String nome,

        @Size(max = 500)
        String descricao,

        @NotNull
        NivelTrilha nivel,

        @NotNull
        CategoriaSkill categoria,

        @NotNull
        TipoSkill tipo
) {}
