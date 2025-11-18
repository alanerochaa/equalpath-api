package com.equalpath.dto;

import com.equalpath.domain.enums.PlataformaCurso;

public record CursoRecomendadoResponseDTO(
        Long id,
        String nome,
        String url,
        Long idTrilha,
        String nomeTrilha,
        PlataformaCurso plataforma,
        Integer duracaoHoras
) {}
