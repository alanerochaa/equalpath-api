package com.equalpath.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SkillDTO {

    private Long id;

    @NotBlank
    private String nome;

    private String categoria;
}
