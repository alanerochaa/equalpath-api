package com.equalpath.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoRecomendadoRequestDTO(

        @NotBlank
        @Size(max = 200)
        String nome,

        @NotBlank
        @Size(max = 400)
        String url,

        @NotNull
        Long idTrilha,

        @NotBlank
        @Size(max = 40)
        String plataforma,

        @NotNull
        @Min(1)
        Integer duracaoHoras
) {}
