package com.equalpath.dto;

import com.equalpath.domain.enums.CategoriaSkill;
import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.TipoSkill;

import java.time.LocalDate;

public record SkillResponseDTO(
        Long id,
        String nome,
        String descricao,
        NivelTrilha nivel,
        CategoriaSkill categoria,
        TipoSkill tipo,
        LocalDate ultimoAcesso
) {}
