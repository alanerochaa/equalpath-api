package com.equalpath.dto;

import com.equalpath.domain.enums.PlataformaCurso;
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

        @NotNull
        PlataformaCurso plataforma,

        @NotNull
        @Min(1)
        Integer duracaoHoras
) {}
