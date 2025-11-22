package com.equalpath.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrilhaRequestDTO(

        @NotBlank
        @Size(max = 150)
        String nome,

        @NotBlank
        @Size(max = 500)
        String descricao,

        @NotBlank
        @Size(max = 40)
        String nivel,

        @NotBlank
        @Size(max = 500)
        String objetivo,

        @NotBlank
        @Size(max = 40)
        String status
) {}
