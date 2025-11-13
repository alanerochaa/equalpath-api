package com.equalpath.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSkillRequestDTO {

    @NotNull
    private Long skillId;

    @NotNull
    @Min(1)
    private Integer nivel;

    private Integer tempoExperienciaMeses;
}
