package com.equalpath.dto;

public record CursoRecomendadoResponseDTO(
        Long id,
        String nome,
        String url,
        Long idTrilha,
        String nomeTrilha,
        String plataforma,
        Integer duracaoHoras
) {}
