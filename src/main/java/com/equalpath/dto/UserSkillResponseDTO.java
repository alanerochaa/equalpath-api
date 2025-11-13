package com.equalpath.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSkillResponseDTO {

    private Long id;
    private Long skillId;
    private String nomeSkill;
    private Integer nivel;
    private Integer tempoExperienciaMeses;
}
